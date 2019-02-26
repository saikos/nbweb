package org.afdemp.cb6.web.messenger.api;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.controller.ControllerHelper;
import org.afdemp.cb6.web.messenger.model.dao.TokenDAO;
import org.afdemp.cb6.web.messenger.model.dao.UserDAO;
import org.afdemp.cb6.web.messenger.model.entity.Role;
import org.afdemp.cb6.web.messenger.model.entity.User;
import org.afdemp.cb6.web.messenger.model.jdbc.TokenDAOImpl;
import org.afdemp.cb6.web.messenger.model.jdbc.UserDAOImpl;

public class UserListAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        try {            
            User user = ControllerHelper.getLoggedInUserFromToken(req, tokenDao, userDao);            
            if (!user.getRole().equals(Role.ADMIN)) {
                throw new MessengerException("Not authorized");
            }
            
            resp.setContentType("application/json; charset=utf-8");
            
            List<User> users = userDao.getAllUsers();
            
            Gson gson = new Gson();
            /*
            String json = gson.toJson(users);                                    
            resp.getWriter().write(json);
            */
            /*
            gson.toJson(users, resp.getWriter());
            */
            JsonHelper.writeJson(resp.getWriter(), users);
        }
        catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonHelper.writeJson(resp.getWriter(), "error", e.getMessage());
        }
    }
}
