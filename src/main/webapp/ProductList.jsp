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
        <li class="logout"><a href="${pageContext.request.contextPath}/Login.jsp">Logout</a></li>
    </ul>
</nav>
<h1>Welcome to the Lab2!</h1>
<c:choose>
    <c:when test="${not empty successMessage}">
        <div style="color: green; margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px; width: 80%; margin: 0 auto;"><h2>${successMessage}</h2></div>
    </c:when>
    <c:when test="${not empty errorMessage}">
        <div style="color: red; margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px; width: 80%; margin: 0 auto;"><h2>${errorMessage}</h2></div>
    </c:when>
</c:choose>
<div style="margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px;">
    <c:forEach var="e" items="${products}">
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
                    <a href="<c:url value='/ordersServlet'>
                                <c:param name='productId' value='${e.getId()}' />
                              </c:url>">Delete</a>
                    <a href="<c:url value='/ordersServlet'>
                                <c:param name='productId' value='${e.getId()}' />
                              </c:url>">Edit</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>

