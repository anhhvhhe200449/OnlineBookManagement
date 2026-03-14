/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.WishlistDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author LOQ
 */
@WebServlet(name = "AddWishlistServlet", urlPatterns = {"/addWishlist"})
public class AddWishlistServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddWishlistServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddWishlistServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        WishlistDAO dao = new WishlistDAO();
        dao.addWishlist(user.getUserID(), bookID);

        response.sendRedirect("book?id=" + bookID);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        processRequest(request, response);
    }

}
