<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        <%@include file="style_products.css"%>>
    </style>
</head>
<body>
<nav>
    <img class="logo" src="images/logo1.png" alt="Logo">
    <ul>
        <li><a href="${pageContext.request.contextPath}/productServlet">Store</a></li>
        <li><a href="${pageContext.request.contextPath}/showOrders">Orders</a></li>
        <li class="logout"><a href="${pageContext.request.contextPath}/login">Logout</a></li>
    </ul>
</nav>
<h1>Your Orders</h1>
<div style="margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px;">
    <c:forEach var="e" items="${orders}">
        <div class="container">
            <div class="card">
                <div class="imgBx">
                    <img src="<c:out value="${e.getImagePath()}"/>" alt="shoes">
                </div>
                <div class="contentBx">
                    <h2><c:out value="${e.nazva}"/></h2>
                    <div class="size">
                        <h3>Price: <fmt:setLocale value="uk_UA"/>
                            <fmt:formatNumber value="${e.getPrice()}" type="currency"/></h3>
                    </div>
                    <a href="<c:url value='/ordersServlet'>
                                <c:param name='productId' value='${e.getId()}' />
                              </c:url>">Buy Now</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>

