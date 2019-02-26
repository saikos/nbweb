package org.afdemp.cb6.web.messenger.model.jdbc;

import java.util.UUID;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.dao.TokenDAO;
import org.afdemp.cb6.web.messenger.model.dao.UserDAO;
import org.afdemp.cb6.web.messenger.model.entity.User;

public class TokenDAOImpl implements TokenDAO {    
    
    @Override
    public String createToken(User user) throws MessengerException {
        try {
            String uuid = UUID.randomUUID().toString();
            
            DatabaseHelper.createToken(uuid, user);
            
            return uuid;
        }
        catch(Exception e) {
            throw new MessengerException(e.getMessage(), e);
        }
    }
    
    @Override
    public User getUserOfToken(String token, UserDAO userDao) throws MessengerException {
        long userId = DatabaseHelper.getUserIdOfToken(token);        
        User user = userDao.getUserById(userId);
        DatabaseHelper.touchToken(token);
        return user;
    }    
}
