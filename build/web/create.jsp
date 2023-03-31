<%-- 
    Document   : create
    Created on : Oct 8, 2022, 4:15:20 PM
    Author     : MY LAPTOP
--%>

<%@page import="dto.ProductError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User Page</title>
    </head>
    <body>
        <h1>Input product's information</h1>
        <form action="MainController" method="POST">
            Product ID <input type="text" name="pID" required=""/>
            <span style="color: red">${requestScope.ERROR.pid}</span></br>
            Product Name <input type="text" name="pName" required=""/>
            <span style="color: red">${requestScope.ERROR.pName}</span></br>
            Image <input type="text" name="image"/></br>
            Price <input type="text" name="price"/>
            <span style="color: red">${requestScope.ERROR.price}</span></br>
            Quantity <input type="number" name="quantity" min="1" required=""/>
            <span style="color: red">${requestScope.ERROR.quantity}</span></br>
            Category ID <input type="text" name="cid" required=""/>
            <span style="color: red">${requestScope.ERROR.cid}</span></br>
            <input type="submit" name="action" value="Create"/></br>
            <input type="reset" value="Reset"/>
        </form>
        <button>
            <a href="admin.jsp" style="text-decoration: none">Back to Search Page</a>
        </button>
        <h1 style="color: red">${requestScope.SUCCESS}</h1>
    </body>
</html>
