package org.afdemp.cb6.web.messenger.model.dao;

import java.util.List;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.entity.Message;
import org.afdemp.cb6.web.messenger.model.entity.User;

public interface MessageDAO {
    
    Message create(User sender, User receiver, String text) throws MessengerException;
    
    void update(Message msg) throws MessengerException;
    
    void delete(Message msg) throws MessengerException;
    
    Message getMessageById(long id) throws MessengerException;
    
    List<Message> getAllMessages() throws MessengerException;
    
    List<Message> getMessagesSentBy(User sender) throws MessengerException;
    
    List<Message> getMessagesSentTo(User receiver) throws MessengerException;
    
    List<Message> getMessagesOfUser(User senderOrReceiver) throws MessengerException;
}
