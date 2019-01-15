package org.afdemp.cb6.web.messenger.model.dao;

import java.util.List;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.entity.Role;
import org.afdemp.cb6.web.messenger.model.entity.User;

public interface UserDAO {
    
    User create(String name, String password, Role role) throws MessengerException;
    
    void update(User user) throws MessengerException;
    
    void delete(User user) throws MessengerException;
    
    User getUserById(long id) throws MessengerException;
    
    User getUser(String username, String password) throws MessengerException;
    
    List<User> getAllUsers() throws MessengerException;
        
}
