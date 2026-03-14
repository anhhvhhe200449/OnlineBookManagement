package controller;

import DAO.BookDAO;
import DAO.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Book;
import model.Category;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private CategoryDAO dao = new CategoryDAO();
    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {

        // ===== TOP 10 VIEW =====
        int page = 1;
        int pageSize = 5;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        int totalBooks = 10;
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        int offset = (page - 1) * pageSize;

        List<Book> books = bookDAO.getTopBooksPaging(offset, pageSize);

        // ===== LATEST UPDATE =====
        int latestPage = 1;
        int latestPageSize = 15;

        String latestPageParam = request.getParameter("latestPage");
        if (latestPageParam != null) {
            latestPage = Integer.parseInt(latestPageParam);
        }

        int totalLatestBooks = bookDAO.countLatestUpdatedBooks();
        int latestTotalPages = (int) Math.ceil((double) totalLatestBooks / latestPageSize);

        if (latestPage < 1) {
            latestPage = 1;
        }
        if (latestPage > latestTotalPages) {
            latestPage = latestTotalPages;
        }

        int latestOffset = (latestPage - 1) * latestPageSize;

        List<Book> latestBooks = bookDAO.getLatestUpdatedBooks(latestOffset, latestPageSize);

        // ===== CATEGORY =====
        List<Category> categories = null;
        try {
            categories = dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== SEND DATA TO JSP =====
        request.setAttribute("list", categories);

        request.setAttribute("books", books);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("latestBooks", latestBooks);
        request.setAttribute("latestPage", latestPage);
        request.setAttribute("latestTotalPages", latestTotalPages);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        processRequest(request, response);
    }

}
