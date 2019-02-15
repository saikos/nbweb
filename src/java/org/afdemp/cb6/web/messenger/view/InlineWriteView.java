package org.afdemp.cb6.web.messenger.view;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.afdemp.cb6.web.messenger.model.entity.Message;
import org.afdemp.cb6.web.messenger.model.entity.User;

public class InlineWriteView extends View {        
    
    public InlineWriteView(ServletConfig config, HttpServletRequest req, HttpServletResponse res) {
        super(config, req, res);
    }
    
    @Override
    public void showErrorMessage(String message) {
        try (Writer w = res.getWriter()) {
            w.write("<!DOCTYPE html><html><head></head>\n");
            w.write("<body><h1>Error</h1>\n");
            w.write(message);
            w.write("</body></html>");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
    }    
    
    @Override
    public void displayLoginScreen(String errorMessage) {
        try (Writer w = res.getWriter()) {
            w.write("<!DOCTYPE html><html><head></head>\n");
            w.write("<body><h1>Login</h1>\n");
            w.write("<form method='post' action='login.html'>\n");
            w.write("<input type='text' name='username' placeholder='Username'>\n");
            w.write("<input type='password' name='password' placeholder='Password'>\n");
            w.write("<button type='submit'>Login</button>\n");
            w.write("</form>\n");
            if (errorMessage != null) {
                w.write("<strong>" + errorMessage + "</strong>");
            }
            w.write("</body></html>");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    @Override
    public void displayHomePage(User user) {
        try (Writer w = res.getWriter()) {
            w.write("<!DOCTYPE html><html><head></head>\n");
            w.write("<body><h1>Home page</h1>Welcome " + user.getName());
            w.write("<p><a href='logout.html'>Logout</a></p></body></html>");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void displayMessageList(User user, String type, List<Message> messages) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
