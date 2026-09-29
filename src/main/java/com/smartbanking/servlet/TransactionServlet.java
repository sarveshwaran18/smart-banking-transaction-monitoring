package com.smartbanking.servlet;

import com.smartbanking.dao.AccountDAO;
import com.smartbanking.dao.TransactionDAO;
import com.smartbanking.model.Account;
import com.smartbanking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = accountDAO.findByUserId(user.getId());

        if (account == null) {
            response.sendRedirect("dashboard?error=Account+not+found");
            return;
        }

        try {
            String type = request.getParameter("type");
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            String description = request.getParameter("description");

            String message = transactionDAO.createTransaction(
                    account.getId(), type, amount, description
            );

            response.sendRedirect("dashboard?message=" +
                    java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8));

        } catch (Exception e) {
            response.sendRedirect("dashboard?error=Invalid+transaction+details");
        }
    }
}
