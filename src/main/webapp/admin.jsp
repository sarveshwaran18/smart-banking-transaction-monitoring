<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.smartbanking.model.Transaction" %>
<%@ page import="java.util.List" %>
<%
    List<Transaction> transactions =
            (List<Transaction>) request.getAttribute("flaggedTransactions");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Monitoring</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="topbar">
    <div>
        <h2>Smart Banking</h2>
        <span>Admin Transaction Monitoring</span>
    </div>
    <a href="logout">Logout</a>
</header>

<main class="container">
    <section class="panel">
        <h2>Flagged Transactions</h2>
        <p class="muted">
            Transactions that match the project's monitoring rules are shown here.
        </p>

        <table>
            <thead>
            <tr>
                <th>Date</th>
                <th>Account</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Reason</th>
            </tr>
            </thead>
            <tbody>
            <% for (Transaction t : transactions) { %>
                <tr>
                    <td><%= t.getTransactionTime() %></td>
                    <td><%= t.getAccountNumber() %></td>
                    <td><%= t.getType() %></td>
                    <td>₹ <%= t.getAmount() %></td>
                    <td><span class="badge danger"><%= t.getAlertReason() %></span></td>
                </tr>
            <% } %>

            <% if (transactions.isEmpty()) { %>
                <tr>
                    <td colspan="5">No flagged transactions found.</td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
