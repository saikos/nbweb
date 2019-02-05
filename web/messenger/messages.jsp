<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="org.afdemp.cb6.web.messenger.model.entity.User" %>
<%@page import="org.afdemp.cb6.web.messenger.model.entity.Message" %>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Messages Page</title>
        <link rel="stylesheet" 
              href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" 
              integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" 
              crossorigin="anonymous">
        <style>
            .custom {
                text-decoration:underline;
                color:red
            }
        </style>
    </head>
    <body>
                
        <% 
        User user = (User) request.getAttribute("user");
        String type = (String) request.getAttribute("type");
        List<Message> messages = (List<Message>) request.getAttribute("messages");                
        String[] types = new String[] { "inbox", "sent" };
        %>
        
        <div class="container">
            <div class="row">
                <div class="col-sm-12 custom">
                    <h1> 
                        <%= user.getName() %> 
                        -
                        <%= type %>
                    </h1>
                </div>
            </div>
        
            <div class="row">                
                
                <div class="col-sm-2">
                    <div class="list-group">
                        <% for(String t: types) { %>
                            <% if (t.equals(type) ) { %>
                                <a href="messages.html?type=<%= t %>" class="list-group-item active">
                                    <%= t %>
                                </a>
                            <% } else { %>
                                <a href="messages.html?type=<%= t %>" class="list-group-item">
                                    <%= t %>
                                </a>
                            <% } %>
                        <% } %>
                    </div>
                </div>
                
                <div class="col-sm-10">
                    
                    <table class="table">
                    <thead>
                      <tr>
                        <th scope="col"><%= type.equals("inbox") ? "Sender" : "Receiver" %></th>
                        <th scope="col">Date</th>
                        <th scope="col">Text</th>                        
                        <th scope="col">Action</th>                        
                      </tr>
                    </thead>
                    <tbody>
                      <% for(Message m: messages) { %>
                      <tr>                        
                        <td><%= type.equals("inbox") ? m.getSender().getName() : m.getReceiver().getName() %> </td>  
                        <td><%= m.getWhen() %> </td>
                        <td><%= m.getText() %></td>
                        <td> 
                            <!-- GET request <a href="delete-message.html?id=<%= m.getId()%>" -->
                            <form method="get" action="delete-message.html" onsubmit="return deleteMessage(this);">
                                <input type="hidden" name="id" value="<%= m.getId()%>">
                                <button type="submit" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal_<%= m.getId()%>">
                                    Delete
                                </button>
                            </form>                            
                            <!--
                            <form method="get" action="forward-message.html">
                                <input type="hidden" name="id" value="<%= m.getId()%>">
                                <button type="submit" class="btn btn-secondary disabled">
                                    Forward
                                </button>
                            </form>
                            -->
                            
                            <div class="modal fade" id="deleteModal_<%= m.getId()%>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                  <div class="modal-content">
                                    <div class="modal-header">
                                      <h5 class="modal-title" id="exampleModalLabel">Delete message</h5>
                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                      </button>
                                    </div>
                                    <div class="modal-body">
                                      Are you sure you want to delete the following message:
                                      
                                      <div class="alert">
                                          <h2><%= m.getText()%></h2>
                                          <p><%= m.getWhen() %></p>
                                      </div>
                                    </div>
                                    <div class="modal-footer">
                                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                      <button type="button" class="btn btn-primary">Save changes</button>
                                    </div>
                                  </div>
                                </div>
                              </div>
                        </td>
                      </tr>                      
                      <% } %>
                    </tbody>
                    </table>
                    
                </div>
            </div>
        </div>

        <!--
        <h1>Messages</h1>        
               
        <p><%= user.getName() %></p>
        
        <p><%= type %></p>
        
        <p><%= messages %></p>
        -->
        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
      
        <script>
            let name = "<%= user.getName()%>";
            //alert(name);                        
            
            function deleteMessage(form) {
                console.log(form);
                return false;
            }
            /*
            $(document).ready( () => {
                
            });
            */
        </script>
    </body>
</html>
