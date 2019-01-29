package org.afdemp.cb6.web.messenger.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.dao.UserDAO;
import org.afdemp.cb6.web.messenger.model.jdbc.UserDAOImpl;
import org.afdemp.cb6.web.messenger.model.entity.User;
import org.afdemp.cb6.web.messenger.view.ForwardToJSPView;
import org.afdemp.cb6.web.messenger.view.InlineWriteView;
import org.afdemp.cb6.web.messenger.view.View;

public class LoginServlet extends HttpServlet {
    
    UserDAO userDao = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        View view = new ForwardToJSPView(req, resp);
                
        try {
            HttpSession session = req.getSession(false);        
            if (session == null) {
                view.displayLoginScreen();
            }
            else {
                Long id = (Long)session.getAttribute("userId");                
                if (id == null) {
                    view.showErrorMessage("Invalid session");
                }
                else {
                    User user = userDao.getUserById(id);
                    view.displayHomePage(user);
                }
            }
        }
        catch(MessengerException me) {
            me.printStackTrace(System.out);
            view.showErrorMessage(me.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        View view = new ForwardToJSPView(req, resp);
        
        try {            
            User user = userDao.getUser(username, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            view.displayHomePage(user);            
        }
        catch(MessengerException me) {
            me.printStackTrace(System.out);
            view.displayLoginScreen(me.getMessage());
        }
    }            
}
