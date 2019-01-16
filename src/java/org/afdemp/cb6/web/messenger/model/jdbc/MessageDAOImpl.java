package org.afdemp.cb6.web.messenger.model.jdbc;

import java.util.List;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.dao.MessageDAO;
import org.afdemp.cb6.web.messenger.model.entity.Message;
import org.afdemp.cb6.web.messenger.model.entity.User;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public Message create(User sender, User receiver, String text) throws MessengerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Message msg) throws MessengerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Message msg) throws MessengerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Message getMessageById(long id) throws MessengerException {
        Message msg = DatabaseHelper.fetchMessageOrNull("select * from message where id = ?", id);
        if (msg == null) {
            throw new MessengerException("Invalid message id: " + id);
        }
        return msg;
    }

    @Override
    public List<Message> getAllMessages() throws MessengerException {
        return DatabaseHelper.fetchMessages("select * from message");
    }

    @Override
    public List<Message> getMessagesSentBy(User sender) throws MessengerException {
        return DatabaseHelper.fetchMessages("select * from message where sender_id = ?", sender.getId());
    }

    @Override
    public List<Message> getMessagesSentTo(User receiver) throws MessengerException {
        return DatabaseHelper.fetchMessages("select * from message where receiver_id = ?", receiver.getId());
    }

    @Override
    public List<Message> getMessagesOfUser(User senderOrReceiver) throws MessengerException {
        long id = senderOrReceiver.getId();
        return DatabaseHelper.fetchMessages("select * from message where sender_id = ? or receiver_id = ?", id, id);
    }
    
    
    
}
