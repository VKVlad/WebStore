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
    <script>
        let userIdForEdit = null;
        let orderIdForEdit = null;
        let goodIdForEdit = null;
        function openModal(orderId, sendTime, count, manager, priceOrder, sendTake, receiver, good) {
            orderIdForEdit = orderId;
            userIdForEdit = receiver;
            goodIdForEdit = good;
            document.getElementById('sendTime').value = sendTime;
            document.getElementById('count').value = count;
            document.getElementById('manager').value = manager;
            document.getElementById('priceOrder').value = priceOrder;
            document.getElementById('sendTake').value = sendTake;

            document.getElementById('editModal').style.display = 'block';
            document.querySelector('.overlay').style.display = 'block';
        }

        function toggleAddForm() {
            openModal(null, '', '');
        }

        function closeModal() {
            document.getElementById('editModal').style.display = 'none';
            document.querySelector('.overlay').style.display = 'none';
        }

        function saveChanges() {
            const good = goodIdForEdit;
            const receiver = userIdForEdit;
            const id = orderIdForEdit;
            const sendTime = document.getElementById('sendTime').value;
            const count = document.getElementById('count').value;
            const priceOrder = document.getElementById('priceOrder').value;
            const sendTake = document.getElementById('sendTake').value;
            const manager = document.getElementById('manager').value;

            const data = {
                manager: manager,
                sendTake: sendTake,
                priceOrder: priceOrder,
                good: good,
                id: id,
                count: count,
                sendTime: sendTime,
                receiver: receiver,
            };

            const xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/editOrder', true);
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        closeModal();
                        window.location.reload();
                    } else {
                        alert('Error saving changes: ' + xhr.responseText);
                    }
                }
            };
            console.log('Data being sent:', data);
            xhr.send(JSON.stringify(data));
        }

        function displayImagePath(event) {
            const input = event.target;
            const filePath = input.value;
            const fileName = "images/" + filePath.split('\\').pop();  // Get the file name from the file path
            document.getElementById('image_path').value = fileName;
        }
    </script>
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
                    <button type="button" onclick="openModal('${e.getId()}', '${e.getSendTime()}', '${e.getCount()}',
                            '${e.getManager()}', '${e.getPriceOrder()}', '${e.getSendTake()}',
                            '${e.getReceiver().getId()}', '${e.getGood().getId()}')"><i class="fas fa-edit"></i></button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="overlay" onclick="closeModal()"></div>
<div id="editModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle" class="modal-title">Edit Order</h2>
        <label for="manager">Manager:</label>
        <input type="text" id="manager" name="manager" disabled><br><br>
        <label for="priceOrder">Price Order:</label>
        <input type="text" id="priceOrder" name="priceOrder" disabled><br><br>
        <label for="sendTake">Take Date:</label>
        <input type="text" id="sendTake" name="sendTake" disabled><br><br>
        <label for="sendTime">Send Date:</label>
        <input type="text" id="sendTime" name="sendTime" required><br><br>
        <label for="count">Count:</label>
        <input type="text" id="count" name="count" required><br><br>
        <div class="button-container">
            <button class="button_save" onclick="saveChanges()">Save</button>
        </div>
    </div>
</div>
</body>
</html>

