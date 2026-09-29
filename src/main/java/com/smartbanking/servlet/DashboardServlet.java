package com.smartbanking.servlet;

import com.smartbanking.dao.AccountDAO;
import com.smartbanking.dao.TransactionDAO;
import com.smartbanking.model.Account;
import com.smartbanking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = accountDAO.findByUserId(user.getId());

        request.setAttribute("account", account);
        request.setAttribute("transactions",
                account == null ? java.util.Collections.emptyList()
                        : transactionDAO.findByAccountId(account.getId()));

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
