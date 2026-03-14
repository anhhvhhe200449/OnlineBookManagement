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
import java.util.List;
import model.Book;
import model.User;

/**
 *
 * @author LOQ
 */
@WebServlet(name = "WishlistServlet", urlPatterns = {"/wishlist"})
public class WishlistServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WishlistServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WishlistServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        WishlistDAO dao = new WishlistDAO();

        List<Book> list = dao.getWishlistByUser(user.getUserID());

        request.setAttribute("wishlist", list);

        request.getRequestDispatcher("MyWishlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        processRequest(request, response);
    }

}
