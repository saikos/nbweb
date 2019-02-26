package org.afdemp.cb6.web.messenger.model.dao;

import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.entity.User;

public interface TokenDAO {

    String createToken(User user) throws MessengerException;
    
    User getUserOfToken(String token, UserDAO userDao) throws MessengerException;
    
}
