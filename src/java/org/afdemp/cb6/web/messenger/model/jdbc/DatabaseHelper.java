package org.afdemp.cb6.web.messenger.model.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.afdemp.cb6.web.messenger.MessengerConfig;
import org.afdemp.cb6.web.messenger.MessengerException;
import org.afdemp.cb6.web.messenger.model.entity.Message;
import org.afdemp.cb6.web.messenger.model.entity.Role;
import org.afdemp.cb6.web.messenger.model.entity.User;

class DatabaseHelper {
                                      //jdbc:mysql://localhost:3306/
    private static final String URL  = "jdbc:mysql://localhost:3306/messenger?serverTimezone=UTC&characterEncoding=utf8&autoReconnect=true";
    private static final String USER = "messAdmin";
    private static final String PASS = "messAdmin123";      
    
    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }
        catch(SQLException sqle) {
            sqle.printStackTrace(System.out);
        }
    }
    
    static User exampleOfProperJdbcHandlingPriorToJava7(String query, long id) throws MessengerException {
        
        Connection con = null;
        PreparedStatement ps = null;
        try {            
            con = openConnection();
            ps = con.prepareStatement(query);
            ps.setLong(1, id);            
            return fetchUserOrNull(ps);
        }        
        catch(SQLException e) {
            throw new MessengerException(e.getMessage(), e);
        }        
        finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            }
            catch(Exception e) {
                System.err.println("Connection or PreparedStatement not closed! Possible memory leak.");
            }
        }
    }
    
    static User fetchUserOrNull(String query, long id) throws MessengerException {
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){            
            ps.setLong(1, id);            
            return fetchUserOrNull(ps);
        }        
        catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }        
    }
    
    static User fetchUserOrNull(String query, String username, String password) throws MessengerException {
                
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){            
            ps.setString(1, username);
            ps.setString(2, password);
            return fetchUserOrNull(ps);
        }        
        catch(SQLException e) {
            throw new MessengerException(e.getMessage(), e);
        }        
    }
    
    static List<User> fetchUsers(String query) throws MessengerException {
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){      
            return fetchUsers(ps);
        }        
        catch(SQLException e) {
            throw new MessengerException(e.getMessage(), e);
        }        
    }
    
    static Message fetchMessageOrNull(String query, long id) throws MessengerException {
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){      
            return fetchMessageOrNull(ps);
        }        
        catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }        
    }
    
    static List<Message> fetchMessages(String query) throws MessengerException {
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){      
            return fetchMessages(ps);
        }        
        catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }        
    }
    
    static List<Message> fetchMessages(String query, long userId) throws MessengerException {
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){      
            ps.setLong(1, userId);
            return fetchMessages(ps);
        }        
        catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }        
    }
    
    static List<Message> fetchMessages(String query, long senderId, long receiverId) throws MessengerException {
        try (Connection con = openConnection();
             PreparedStatement ps = con.prepareStatement(query);    
        ){      
            ps.setLong(1, senderId);
            ps.setLong(2, receiverId);
            return fetchMessages(ps);
        }        
        catch(SQLException e) {
            throw new MessengerException(e.getMessage(), e);
        }        
    }
    
    private static Connection openConnection() throws SQLException {                       
        MessengerConfig cfg = MessengerConfig.getInstance();        
        return DriverManager.getConnection(
            cfg.getProperty("jdbcURL"), 
            cfg.getProperty("jdbcUser"), 
            cfg.getProperty("jdbcPass")
        );                       
    }        
    
    private static User fetchUserOrNull(PreparedStatement ps) throws SQLException, MessengerException {
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createUser(rs);
        }
        else {
            return null;
        }
    }
    
    private static List<User> fetchUsers(PreparedStatement ps) throws SQLException, MessengerException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User u = createUser(rs);
            users.add(u);
        }  
        return users;
    }
    
    private static User createUser(ResultSet rs) throws SQLException, MessengerException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        Role r = Role.getRoleFor(rs.getInt("rid"));
        user.setRole(r);
        return user;
    }
    
    private static Message fetchMessageOrNull(PreparedStatement ps) throws SQLException, MessengerException {
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createMessage(rs);
        }
        else {
            return null;
        }
    }
    
    private static List<Message> fetchMessages(PreparedStatement ps) throws SQLException, MessengerException {
        ArrayList<Message> messages = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Message m = createMessage(rs);
            messages.add(m);
        }  
        return messages;
    }
    
    private static Message createMessage(ResultSet rs) throws SQLException, MessengerException {
        Message msg = new Message();
        
        msg.setId(rs.getLong("id"));                
        
        Long senderId = rs.getLong("sender_id");
        User sender = fetchUserOrNull("select * from user where id = ?", senderId);
        msg.setSender(sender);
        
        Long receiverId = rs.getLong("receiver_id");
        User receiver = fetchUserOrNull("select * from user where id = ?", receiverId);               
        msg.setReceiver(receiver);
        
        Date when = new Date(rs.getTimestamp("when").getTime());
        msg.setWhen(when);
        
        msg.setText(rs.getString("text"));
        
        return msg;
    }
}
