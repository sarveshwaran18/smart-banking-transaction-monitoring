<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Banking - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="login-page">
<div class="login-card">
    <h1>Smart Banking</h1>
    <p class="subtitle">Transaction Monitoring System</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="login" method="post">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">Sign In</button>
    </form>

    <div class="demo-box">
        <strong>Demo accounts</strong><br>
        Customer: customer1 / customer123<br>
        Admin: admin / admin123
    </div>
</div>
</body>
</html>
