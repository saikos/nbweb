package org.afdemp.cb6.web.messenger;

public class MessengerException extends Exception {
    
    public MessengerException(String message) {
        super(message);
    }
    
    public MessengerException(String message, Throwable cause) {
        super(message, cause);
    }
}
