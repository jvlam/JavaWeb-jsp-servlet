<%-- 
    Document   : admin
    Created on : Sep 28, 2022, 5:08:17 PM
    Author     : MY LAPTOP
--%>

<%@page import="dto.Product"%>
<%@page import="dto.Product"%>
<%@page import="dto.UserDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        welcome admin: <h1 style="font-size: 50px;">${sessionScope.LOGIN_USER.fullName}</h1>
        <!--Search-->
        <form action="MainController" method="post">
            Search: <input type="text" name="search" value="${param.search}"/>
            <input hidden="" type="text" name="admin" value="adminSearch"/>
            <input type="submit" name="action" value="Search"/>
        </form>
        <!--Logout-->
        <a href="create.jsp">Create Product</a>
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Logout"/>
        </form>
        <c:if test="${requestScope.LIST_PRODUCT != null}">
            <c:if test="${not empty requestScope.LIST_PRODUCT}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>image</th>
                            <th>price</th>
                            <th>quantity</th>
                            <th>Category ID</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${requestScope.LIST_PRODUCT}" varStatus="counter">
                            <form action="MainController" method="POST">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        ${p.id}
                                    </td>
                                    <td>
                                        <input type="text" name="pName" value="${p.productName}"/>
                                    </td>
                                    <td>
                                        <input type="text" name="image" value="${p.image}"/>
                                    </td>
                                    <td>
                                        <input type="text" name="price" value="${p.price}"/>
                                    </td>
                                    <td>
                                        <input type="number" min="1" name="quantity" value="${p.quantity}"/>
                                    </td>
                                    <td>
                                        <input type="text" name="cId" value="${p.category.categoryID}"/>
                                    </td>
                                    <td>
                                        <c:url var="delete" value="MainController">
                                            <c:param name="pID" value="${p.id}"></c:param>
                                            <c:param name="search" value="${param.search}"></c:param>
                                            <c:param name="action" value="AdminDelete"></c:param>
                                            <c:param name="admin" value="adminSearch"></c:param>
                                        </c:url>
                                        <a href="${delete}">Delete</a>
                                    </td>
                                    <td>
                                        <input type="submit" name="action" value="UpdateProduct">
                                        <input type="hidden" name="pId" value="${p.id}">
                                        <input type="hidden" name="search" value="${param.search}">
                                        <input type="hidden" name="admin" value="adminSearch">
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </tbody>
            </table>
        </c:if>
    </c:if>
    <h1 style="color: red">${requestScope.ERROR}</h1>
    <h1 style="color: red">${requestScope.SUCCESS}</h1>
    
</body>
</html>
