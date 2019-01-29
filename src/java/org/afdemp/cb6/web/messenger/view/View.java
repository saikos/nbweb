package org.afdemp.cb6.web.messenger.view;

import java.io.IOException;
import java.io.Writer;
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
    
    public void showErrorMessage(String message) throws IOException {
        Writer w = res.getWriter();
        w.write("<!DOCTYPE html><html><head></head>\n");
        w.write("<body><h1>Error</h1>\n");
        w.write(message);
        w.write("</body></html>");
        w.close();
    }
    
    public void displayLoginScreen() throws IOException {
        displayLoginScreen(null);
    }
    
    public void displayLoginScreen(String errorMessage) throws IOException {
        Writer w = res.getWriter();
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
        w.close();
    }
    
    public void displayHomePage(User user) throws IOException {
        Writer w = res.getWriter();
        w.write("<!DOCTYPE html><html><head></head>\n");
        w.write("<body><h1>Home page</h1>Welcome " + user.getName() + "</body></html>");
        w.close();
    }
    
}
