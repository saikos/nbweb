package org.afdemp.cb6.web.messenger.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.dao.UserDAO;
import org.afdemp.cb6.web.messenger.model.jdbc.UserDAOImpl;
import org.afdemp.cb6.web.messenger.model.entity.User;
import org.afdemp.cb6.web.messenger.view.View;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new View(req, resp).displayLoginScreen();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        View view = new View(req, resp);
        
        try {
            UserDAO userDao = new UserDAOImpl();
            User user = userDao.getUser(username, password);
            view.displayHomePage(user);            
        }
        catch(MessengerException me) {
            view.displayLoginScreen(me.getMessage());
        }
    }            
}
