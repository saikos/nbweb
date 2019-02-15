package org.afdemp.cb6.web.messenger.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
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
        
    //Override this method if you need access to the specific
    //servlet's init parameters
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        //Read the init paramter(s) here, for example: 
        //String textsPath = config.getInitParameter(Constants.TEXTS_PATH_INIT_PARAM_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {               
        
        View view = new ForwardToJSPView(this.getServletConfig(), req, resp);
        
        try {
            view.prepareI18N();
            
            User user = ControllerHelper.getLoggedInUserFromCookie(req, new UserDAOImpl());

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
