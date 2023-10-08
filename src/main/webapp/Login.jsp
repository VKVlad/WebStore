<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style>
        <%@include file="css/style.css"%>
    </style>
</head>
<body>
<div class="login-wrap">
    <div class="login-html">
        <form action="${pageContext.request.contextPath}/login" method="post" class="sign-up-form" onsubmit="return checkPasswords()">
            <input type="hidden" name="tab" value="sign-in">
            <div class="group">
                <label for="user" class="label">Username</label>
                <input id="user1" type="text" name="username" class="input" required>
            </div>
            <div class="group">
                <label for="pass" class="label">Password</label>
                <input id="pass2" type="password" name="password" class="input" data-type="password" required>
            </div>
            <c:if test="${not empty errorMessage}">
                <div style="color: red;">${errorMessage}</div>
            </c:if>
            <div class="group">
                <input type="submit" class="button" value="Sign In">
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/registration" method="post" class="sign-up-form" onsubmit="return checkPasswords()">
            <input type="hidden" name="tab" value="sign-up">
            <div class="group">
                <label for="user" class="label">Username</label>
                <input id="user" type="text" name="usernamereg" class="input" required>
            </div>
            <div class="group">
                <label for="pass2" class="label">Password</label>
                <input id="pass" type="password" name="passwordreg" class="input" data-type="password" required>
            </div>
            <div class="group">
                <label for="pass" class="label">Repeat Password</label>
                <input id="pass4" type="password" name="repeatPassword" class="input" data-type="password" required>
            </div>
            <div class="group">
                <span id="error-message" style="color: red;"></span> <!-- Отображение ошибки -->
            </div>
            <c:if test="${not empty errorMessageReg}">
                <div style="color: red;">${errorMessageReg}</div>
            </c:if>
            <div class="group">
                <input type="submit" class="button" value="Sign Up">
            </div>
        </form>
    </div>
</div>
<script>
    function checkPasswords() {
        var password1 = document.getElementById("pass").value;
        var password2 = document.getElementById("pass4").value;
        var errorMessage = document.getElementById("error-message");

        if (password1 !== password2) {
            errorMessage.textContent = "Passwords do not match.";
            return false; // Отмена отправки формы
        }

        errorMessage.textContent = ""; // Очистка сообщения об ошибке, если пароли совпадают
        return true; // Продолжение отправки формы
    }
</script>
</body>
</html>
