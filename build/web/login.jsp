<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action="MainController" method="POST">
            User ID: <input type="text" name="userID" /><br>
            Password: <input type="password" name="password" /><br>
            <input type="submit" name="action" value="Login"/>
            <input type="reset" value="Reset"/>
        </form>
        <a href="https://accounts.google.com/o/oauth2/auth/oauthchooseaccount
?redirect_uri=http://localhost:8080/SE1606_T4S4_JSP_JSTL/MainController?action=LoginGoogle
&scope=email%20profile
&response_type=code
&client_id=467082523739-1n63lt2spp56adq3a3r4ha7ak4146rkn.apps.googleusercontent.com
&flowName=GeneralOAuthFlow">Login Google</a>    
        <h3 style="color: red">${requestScope.ERROR}</h3>
    </body>
</html>