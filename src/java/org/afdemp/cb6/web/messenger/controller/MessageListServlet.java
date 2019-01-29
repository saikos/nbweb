package org.afdemp.cb6.web.messenger.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.dao.MessageDAO;
import org.afdemp.cb6.web.messenger.model.entity.Message;
import org.afdemp.cb6.web.messenger.model.entity.User;
import org.afdemp.cb6.web.messenger.model.jdbc.MessageDAOImpl;
import org.afdemp.cb6.web.messenger.model.jdbc.UserDAOImpl;
import org.afdemp.cb6.web.messenger.view.ForwardToJSPView;
import org.afdemp.cb6.web.messenger.view.View;

public class MessageListServlet extends HttpServlet {        
    
    public static final String TYPE_INBOX = "inbox";
    public static final String TYPE_SENT  = "sent";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        View view = new ForwardToJSPView(req, resp);
        
        try {
            User user = ControllerHelper.
                    getLoggedInUser(req, new UserDAOImpl());

            String type = req.getParameter("type");
            if (type == null) {
                type = TYPE_INBOX;
            }
            else {
                if (!type.equals(TYPE_INBOX) && 
                    !type.equals(TYPE_SENT)) {
                    throw new MessengerException("Invalid input");
                }
            }

            MessageDAO msgDao = new MessageDAOImpl();
            List<Message> messages = null;
            if (type.equals(TYPE_INBOX)) {
                messages = msgDao.getMessagesSentTo(user);
            }
            else {
                messages = msgDao.getMessagesSentBy(user);
            }
            
            view.displayMessageList(user, type, messages);
        }
        catch(MessengerException me) {
            view.showErrorMessage(me.getMessage());
        }
    }
    
    
    
}
