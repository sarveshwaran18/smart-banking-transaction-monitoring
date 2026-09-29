package com.smartbanking.servlet;

import com.smartbanking.dao.TransactionDAO;
import com.smartbanking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setAttribute("flaggedTransactions",
                transactionDAO.findFlaggedTransactions());

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}
