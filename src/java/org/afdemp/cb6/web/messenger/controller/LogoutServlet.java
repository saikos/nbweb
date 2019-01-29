package org.afdemp.cb6.web.messenger.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.afdemp.cb6.web.messenger.view.ForwardToJSPView;
import org.afdemp.cb6.web.messenger.view.View;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        View view = new ForwardToJSPView(req, resp);
        view.displayLoginScreen();
    }
    
}
