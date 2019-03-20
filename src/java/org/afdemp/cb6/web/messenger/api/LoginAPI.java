package org.afdemp.cb6.web.messenger.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.afdemp.cb6.web.messenger.model.dao.TokenDAO;
import org.afdemp.cb6.web.messenger.model.dao.UserDAO;
import org.afdemp.cb6.web.messenger.model.entity.User;
import org.afdemp.cb6.web.messenger.model.jdbc.TokenDAOImpl;
import org.afdemp.cb6.web.messenger.model.jdbc.UserDAOImpl;

public class LoginAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        try {
            User user = userDao.getUser(username, password);
            String token = tokenDao.createToken(user);
            resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            resp.setContentType("application/json; charset=utf-8");

            JsonHelper.writeJson(resp.getWriter(), "token", token);
        }
        catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonHelper.writeJson(resp.getWriter(), "error", e.getMessage());
        }
    }
    
    
}
