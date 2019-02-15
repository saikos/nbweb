package org.afdemp.cb6.web.messenger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.dao.UserDAO;
import org.afdemp.cb6.web.messenger.model.entity.User;

public class ControllerHelper {

    static User getLoggedInUserFromCookie(HttpServletRequest req, UserDAO userDao) throws MessengerException {        
        HttpSession session = req.getSession(false);        
        if (session == null) {
            throw new MessengerException("Not authorized");
        }
        Long id = (Long)session.getAttribute("userId");                
        if (id == null) {
            throw new MessengerException("Invalid session");
        }
        return userDao.getUserById(id);        
    }
            
}
