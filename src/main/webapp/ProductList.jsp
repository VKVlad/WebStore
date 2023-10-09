<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        <%@include file="css/style_products.css"%>
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script>
        let productIdForEdit = null;
        function openModal(productId, productName, price, priceOpt, category, article, image_path) {
            document.getElementById('modalTitle').innerText = productId ? "Edit Product" : "Add Product";

            productIdForEdit = productId;
            if (productId) {
                document.getElementById('productName').value = decodeURIComponent(productName);
                document.getElementById('price').value = price;
                document.getElementById('priceOpt').value = priceOpt;
                document.getElementById('category').value = category;
                document.getElementById('article').value = article;
                document.getElementById('image_path').value = image_path;
            } else {
                document.getElementById('productName').value = '';
                document.getElementById('price').value = '';
                document.getElementById('priceOpt').value = '';
                document.getElementById('category').value = '';
                document.getElementById('article').value = '';
                document.getElementById('image_path').value = '';
            }

            document.getElementById('editModal').style.display = 'block';
            document.querySelector('.overlay').style.display = 'block';
        }


        function toggleAddForm() {
            openModal(null, '', '', '', '', '', '');  // Open modal for adding a new product
        }

        function closeModal() {
            document.getElementById('editModal').style.display = 'none';
            document.querySelector('.overlay').style.display = 'none';
        }

        function saveChanges() {
            const id = productIdForEdit;
            const nazva = decodeURIComponent(document.getElementById('productName').value);
            const price = document.getElementById('price').value;
            const priceOpt = document.getElementById('priceOpt').value;
            const category = document.getElementById('category').value;
            const article = document.getElementById('article').value;
            const imagePath = document.getElementById('image_path').value;

            const data = {
                id: id,
                nazva: nazva,
                price: price,
                priceOpt: priceOpt,
                category: category,
                article: article,
                imagePath: imagePath
            };

            const xhr = new XMLHttpRequest();
            xhr.open('POST', productIdForEdit ? '${pageContext.request.contextPath}/editGood' : '${pageContext.request.contextPath}/addGood', true);
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
<div style="display: flex; justify-content: space-between; align-items: center; margin-left: 37.7%;">
    <h1>Welcome to the Lab2!</h1>
    <button onclick="toggleAddForm()" class="add-button">+</button>
</div>

<c:choose>
    <c:when test="${not empty successMessageDelete}">
        <div style="color: green; margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px; width: 80%; margin: 0 auto;"><h2>${successMessageDelete}</h2></div>
    </c:when>
    <c:when test="${not empty errorMessageDelete}">
        <div style="color: red; margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px; width: 80%; margin: 0 auto;"><h2>${errorMessageDelete}</h2></div>
    </c:when>
    <c:when test="${not empty successMessage}">
        <div style="color: green; margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px; width: 80%; margin: 0 auto;"><h2>${successMessage}</h2></div>
    </c:when>
    <c:when test="${not empty errorMessage}">
        <div style="color: red; margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px; width: 80%; margin: 0 auto;"><h2>${errorMessage}</h2></div>
    </c:when>
</c:choose>
<div style="margin-top: 16px; display: flex; flex-wrap: wrap; justify-content: center; gap: 100px;">
    <c:forEach var="e" items="${products}">
        <div class="card">
            <c:url var="deleteUrl" value="/deleteGood">
                <c:param name="productId" value="${e.getId()}" />
            </c:url>
            <form action="${deleteUrl}" method="post" class="delete-button-form">
                <button type="submit" class="delete-button">&#10006;</button>
            </form>
            <div class="imgBx">
                <img src="<c:out value="${e.getImagePath()}"/>" alt="shoes">
            </div>
            <div class="contentBx">
                <h2><c:out value="${e.nazva}"/></h2>
                <div class="size">
                    <h3>Price: <fmt:setLocale value="uk_UA"/>
                        <fmt:formatNumber value="${e.getPrice()}" type="currency"/></h3>
                    <h4>Price Opt: <fmt:setLocale value="uk_UA"/>
                        <fmt:formatNumber value="${e.getPriceOpt()}" type="currency"/></h4>
                </div>
                <div class="button-container">
                    <c:url var="buyUrl" value="/ordersServlet">
                        <c:param name="productId" value="${e.getId()}" />
                    </c:url>
                    <form action="${buyUrl}" method="post" class="">
                        <input type="number" name="quantity" min="1" value="1" required>
                        <button type="submit">Buy</button>
                    </form>
                    <button type="button" onclick="openModal('${e.getId()}', '${e.getNazva()}', '${e.getPrice()}', '${e.getPriceOpt()}', '${e.getCategory()}', '${e.getArticle()}', '${e.getImagePath()}')"><i class="fas fa-edit"></i></button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="overlay" onclick="closeModal()"></div>
<div id="editModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle" class="modal-title"></h2>
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required><br><br>
        <label for="price">Price:</label>
        <input type="text" id="price" name="price" required><br><br>
        <label for="priceOpt">Price Opt:</label>
        <input type="text" id="priceOpt" name="priceOpt" required><br><br>
        <label for="category">Сategory:</label>
        <input type="text" id="category" name="category" required><br><br>
        <label for="article">Article:</label>
        <input type="text" id="article" name="article" required><br><br>
        <label for="image_path">Image:</label>
        <input type="text" id="image_path" name="image_path" disabled><br><br>
        <input type="file" id="image_file" name="image_file" accept="image/*" onchange="displayImagePath(event)"><br><br>
        <div class="button-container">
            <button class="button_save" onclick="saveChanges()">Save</button>
        </div>
    </div>
</div>
</body>
</html>