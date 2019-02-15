package org.afdemp.cb6.web.messenger.view;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.controller.Constants;
import org.afdemp.cb6.web.messenger.model.entity.Message;
import org.afdemp.cb6.web.messenger.model.entity.User;

public abstract class View {
    
    protected final ServletConfig config;
    protected final HttpServletRequest req;
    protected final HttpServletResponse res;        
    
    public View(ServletConfig config,
                HttpServletRequest req,
                HttpServletResponse res) {
        this.config = config;
        this.req = req;
        this.res = res;
    }
    
    public void prepareI18N() throws MessengerException {
        //This is an example of how you can read a context input parameter
        //(one that is available to all servlets)
        String path = config.getServletContext().getInitParameter(Constants.TEXTS_PATH_INIT_PARAM_NAME);
        try(Reader r =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(path),
                        "utf-8"
                    )
                );
            ) {
            Gson gson = new Gson();
            HashMap texts = gson.fromJson(r, HashMap.class);
            I18N i18n = new I18N(texts);
            req.setAttribute("i18n", i18n);
            
            req.setAttribute("lang", getCurrentLanguage());
        }
        catch(Exception e) {
            throw new MessengerException(e.getMessage(), e);
        }
    }        
    
    public abstract void showErrorMessage(String message);
    
    public void displayLoginScreen() {
        displayLoginScreen(null);
    }
    
    public abstract void displayLoginScreen(String errorMessage);
    
    public abstract void displayHomePage(User user);
    
    public abstract void displayMessageList(User user, String type, List<Message> messages);        
    
    public String getCurrentLanguage() {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return "el";
        }
        String lang = (String) session.getAttribute("lang");
        return (lang == null ? "el" : lang);
    }
}
