package org.afdemp.cb6.web.messenger.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.afdemp.cb6.web.messenger.model.entity.User;

public class View {
    
    private final HttpServletRequest req;
    private final HttpServletResponse res;
    
    public View(HttpServletRequest req, HttpServletResponse res) {
        this.req = req;
        this.res = res;
    }
    
    public void displayLoginScreen() {
        displayLoginScreen(null);
    }
    
    public void displayLoginScreen(String errorMessage) {
        
    }
    
    public void displayHomePage(User user) {
        
    }
    
}
