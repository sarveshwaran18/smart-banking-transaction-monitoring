<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.smartbanking.model.User,com.smartbanking.model.Account,com.smartbanking.model.Transaction" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    Account account = (Account) request.getAttribute("account");
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="topbar">
    <div>
        <h2>Smart Banking</h2>
        <span>Transaction Monitoring System</span>
    </div>
    <div>
        Welcome, <strong><%= user.getFullName() %></strong>
        &nbsp; | &nbsp; <a href="logout">Logout</a>
    </div>
</header>

<main class="container">
    <% if (request.getParameter("message") != null) { %>
        <div class="alert success"><%= request.getParameter("message") %></div>
    <% } %>

    <% if (request.getParameter("error") != null) { %>
        <div class="alert error"><%= request.getParameter("error") %></div>
    <% } %>

    <% if (account != null) { %>
    <section class="summary">
        <div class="card">
            <h3>Account Number</h3>
            <p><%= account.getAccountNumber() %></p>
        </div>
        <div class="card">
            <h3>Available Balance</h3>
            <p class="balance">₹ <%= account.getBalance() %></p>
        </div>
    </section>

    <section class="panel">
        <h2>New Transaction</h2>
        <form action="transaction" method="post" class="transaction-form">
            <select name="type" required>
                <option value="DEPOSIT">Deposit</option>
                <option value="WITHDRAWAL">Withdrawal</option>
            </select>

            <input type="number" name="amount" min="1" step="0.01"
                   placeholder="Amount" required>

            <input type="text" name="description"
                   placeholder="Description" maxlength="255">

            <button type="submit">Submit Transaction</button>
        </form>
    </section>

    <section class="panel">
        <h2>Transaction History</h2>
        <table>
            <thead>
            <tr>
                <th>Date</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Description</th>
                <th>Monitoring</th>
            </tr>
            </thead>
            <tbody>
            <% for (Transaction t : transactions) { %>
                <tr>
                    <td><%= t.getTransactionTime() %></td>
                    <td><%= t.getType() %></td>
                    <td>₹ <%= t.getAmount() %></td>
                    <td><%= t.getDescription() == null ? "" : t.getDescription() %></td>
                    <td>
                        <% if (t.isFlagged()) { %>
                            <span class="badge danger">Flagged</span>
                        <% } else { %>
                            <span class="badge safe">Normal</span>
                        <% } %>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </section>
    <% } %>
</main>
</body>
</html>
