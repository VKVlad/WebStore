<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        <%@include file="css/style_orders.css"%>>
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<nav>
    <img class="logo" src="images/logo1.png" alt="Logo">
    <ul>
        <li><a href="${pageContext.request.contextPath}/productServlet">Store</a></li>
        <li><a href="${pageContext.request.contextPath}/showOrders">Orders</a></li>
        <li class="logout"><a href="${pageContext.request.contextPath}/Login.jsp">Logout</a></li>
    </ul>
</nav>
<h1>Your Orders</h1>
<div style="margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px;">
    <c:forEach var="e" items="${orders}">
        <div class="card">
            <c:url var="deleteUrl" value="/deleteOrder">
                <c:param name="orderId" value="${e.getId()}" />
            </c:url>
            <form action="${deleteUrl}" method="post" class="delete-button-form">
                <button type="submit" class="delete-button">&#10006;</button>
            </form>
            <div class="imgBx">
                <img src="<c:out value="${e.getGood().getImagePath()}"/>" alt="shoes">
            </div>
            <div class="contentBx">
                <h2><c:out value="${e.getGood().nazva}"/></h2>
                <div class="size">
                    <h3>Total Price: <fmt:setLocale value="uk_UA"/>
                        <fmt:formatNumber value="${e.getPriceOrder()}" type="currency"/></h3>
                    <h4>Count: <c:out value="${e.getCount()}"/></h4>
                </div>

                <div class="button-container">
                    <c:url var="editUrl" value="/editOrder">
                        <c:param name="orderId" value="${e.getId()}" />
                    </c:url>
                    <form action="${editUrl}" method="get">
                        <button type="button"><i class="fas fa-edit"></i></button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>

