/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import model.Book;
import model.Category;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BookListServlet", urlPatterns = {"/admin/book"})
public class BookListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private CategoryDAO c = new CategoryDAO();
    private BookDAO b = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        try {
            //check role login

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            User user = (User) session.getAttribute("user");
            if ("user".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                action = "list";
            }
            switch (action) {
                case "add":
                    showAddBookForm(request, response);
                    break;
                case "edit":
                    showEditBookForm(request, response);
                    break;
                case "delete":
                    deleteBook(request, response);
                    break;
                default:
                    listBook(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) {
                response.sendRedirect(request.getContextPath() + "/admin/category");
                return;
            }
            switch (action) {
                case "add":
                    insertBook(request, response);
                    break;
                case "edit":
                    updateBook(request, response);
                    break;
                case "delete":
                    deleteBook(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    //form add book
    private void showAddBookForm(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        request.setAttribute("categoryID", categoryID);
        request.getRequestDispatcher("/admin/book-add.jsp").forward(request, response);
    }

    //form edit book
    private void showEditBookForm(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        int id = Integer.parseInt(request.getParameter("id"));
        Book book = b.getBookByID(id);

        request.setAttribute("book", book);

        request.setAttribute("categoryID", categoryID);
        request.getRequestDispatcher("/admin/book-edit.jsp").forward(request, response);
    }

    //all book by category
    private void listBook(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {

        String categoryIDRaw = request.getParameter("categoryID");

        if (categoryIDRaw == null) {
            response.sendRedirect(request.getContextPath() + "/admin/category");
            return;
        }

        int categoryID = Integer.parseInt(categoryIDRaw);

        List<Book> booklist = b.getBookByCategory(categoryID);

        request.setAttribute("booklist", booklist);
        request.setAttribute("categoryID", categoryID);

        request.getRequestDispatcher("/admin/book-list.jsp").forward(request, response);
    }

    //edit book
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        String title = request.getParameter("title");
        String des = request.getParameter("des");
        String coverImage = request.getParameter("coverImage");
        String author = request.getParameter("author");
        int id = Integer.parseInt(request.getParameter("id"));  //id sách
        int categoryID = Integer.parseInt(request.getParameter("categoryID")); //id của category chứa sách đó

        boolean check = b.updateBook(title, des, coverImage, author, id);
        if (!check) {
            request.setAttribute("error", "This book can't be edited!");
            request.setAttribute("categoryID", categoryID);
            request.setAttribute("id", id);
            request.getRequestDispatcher("/admin/book-edit.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/book?action=list&categoryID=" + categoryID);
        }

    }

    //addBook
    private void insertBook(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        String title = request.getParameter("title");
        String des = request.getParameter("des");
        String coverImage = request.getParameter("coverImage");
        String author = request.getParameter("author");
        int id = Integer.parseInt(request.getParameter("id"));

        boolean check = b.insertBook(title, des, coverImage, id, author);
        if (!check) {
            request.setAttribute("error", "This book already exists!");
            request.setAttribute("categoryID", id);
            request.getRequestDispatcher("/admin/book-add.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/book?action=list&categoryID=" + id);
        }

    }

    //delete book
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {

        int id = Integer.parseInt(request.getParameter("id"));
        int categoryID = Integer.parseInt(request.getParameter("categoryID")); //id của category chứa sách đó

        b.deleteBook(id);
        response.sendRedirect(request.getContextPath() + "/admin/book?action=list&categoryID=" + categoryID);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
