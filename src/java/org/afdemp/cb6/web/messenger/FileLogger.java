package org.afdemp.cb6.web.messenger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.afdemp.cb6.web.messenger.model.entity.Message;

public class FileLogger {
    
    private FileWriter writer;
    private static final String FILENAME = "messenger.log";
    
    //Open the file or create it if not found
    public void initFile() throws MessengerException {
        try {
            writer = new FileWriter(FILENAME, true);
        }
        catch(IOException ex) {
            throw new MessengerException(ex.getMessage(), ex);
        }
    }
    
    public void writeToFile(Message msg) throws MessengerException{
        try {
            writer.append(msg.getId() + ", "
                    + msg.getSender().getName()
                    +" , "+ msg.getReceiver().getName()
                    +" , "+ msg.getWhen()+ " , "
                    + msg.getText()
            );
        } catch (IOException ex) {
            throw new MessengerException(ex.getMessage(), ex);
        }
    }
}
