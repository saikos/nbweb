<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<%@page import="org.afdemp.cb6.web.messenger.model.entity.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <h1>Home page</h1>
        <% User user = (User) request.getAttribute("user"); %>
        Welcome <%= user.getName()%>
        <p><a href="messages.html">Messages</a></p>
        <p><a href="logout.html">Logout</a></p>
    </body>
</html>
