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
                            <button type="button" class="btn btn-danger deleteMessageButton" data-id="<%= m.getId() %>" data-when="<%= m.getWhen() %>" data-text="<%= m.getText() %>">
                                Delete
                            </button>                                                        
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
        
        <div class="modal fade" id="deleteMessageConfirmation" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                        <h2 class="message-text"></h2>
                        <p class="message-when"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                        <button id="deleteMessageModalButton" type="button" class="btn btn-danger">Yes</button>
                    </div>
                </div>
            </div>
        </div>
        
        <form id="deleteMessageForm" method="get" action="delete-message.html">
            <input type="hidden" name="id" />            
        </form>                            
        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
      
        <script>
            let name = "<%= user.getName()%>";
            //alert(name);                        
                        
            $(document).ready( () => {
                
                let selectedMessageForDeletion = {
                   id: null,
                   when: null
                };
                
                let selectedMessageIdForDeletion = null;
                
                /*
                let forms = $("form.delete-message");
                forms.each( (i, el) => {
                    console.log("I am a form: ", i, " = ", el);
                }).submit( (e) => {
                    console.log("Event: ", e, " - submitting?");
                    $("#deleteMessageConfirmation").modal('show');
                    //This does not block so that we can wait for the 
                    //user to decide.
                    console.log("Not submitting!")
                    return false;
                });
                */
               
                $("button.deleteMessageButton").click( (e) => {
                    console.log("Clicked ", e);
                    let btn = $(e.target);
                    let id = btn.data('id');
                    selectedMessageIdForDeletion = id;
                    let when = btn.data('when');
                    let text = btn.data('text');
                    
                    console.log("Deleting: ", id, ", ", when, ", ", text);
                    
                    let modal = $("#deleteMessageConfirmation");
                    modal.find('.message-text').text(text);
                    modal.find('.message-when').text(when);
                    
                    modal.modal('show');                                        
                });               
                
                $("#deleteMessageModalButton").click ( (e) => {
                    console.log("Submitting the form");
                    let form = $("#deleteMessageForm");
                    form.find("input[type=hidden]").val(selectedMessageIdForDeletion);
                    form.submit();
                });
               
            });            
        </script>
    </body>
</html>
