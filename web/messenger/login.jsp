<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="login.html">
            <input type="text" name="username" placeholder="Username">
            <input type="password" name="password" placeholder="Password">
            <button type="submit">Login</button>
        </form>
        
        <% 
        String errorMessage = (String) request.getAttribute("errorMessage"); 
        if (errorMessage != null) {
        %>
            <strong><%= errorMessage %></strong>
        <%
        }
        %>
    </body>
</html>
