package org.afdemp.cb6.web.messenger.view;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.afdemp.cb6.web.messenger.model.entity.User;

public abstract class View {
    
    protected final HttpServletRequest req;
    protected final HttpServletResponse res;
    
    public View(HttpServletRequest req,
                HttpServletResponse res) {
        this.req = req;
        this.res = res;
    }
    
    public abstract void showErrorMessage(String message);
    
    public void displayLoginScreen() {
        displayLoginScreen(null);
    }
    
    public abstract void displayLoginScreen(String errorMessage);
    
    public abstract void displayHomePage(User user);
}
