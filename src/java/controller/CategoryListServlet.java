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
import model.Category;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CategoryListServlet", urlPatterns = {"/admin/category"})
public class CategoryListServlet extends HttpServlet {

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
            out.println("<title>Servlet CategoryListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryListServlet at " + request.getContextPath() + "</h1>");
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
    private CategoryDAO dao = new CategoryDAO();

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
                    showAddForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listCategory(request, response);
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
                    insertCategory(request, response);
                    break;
                case "edit":
                    updateCategory(request, response);
                    break;
                case "delete":
                    deleteCategory(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    //show list category
    private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Category> list = dao.getAll();
        request.setAttribute("list", list);
        request.getRequestDispatcher("/admin/category-list.jsp").forward(request, response);
    }

    //show add form
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.getRequestDispatcher("/admin/category-add.jsp").forward(request, response);
    }

    //show edit form
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category c = dao.getByID(id);
        request.setAttribute("category", c);
        request.getRequestDispatcher("/admin/category-edit.jsp").forward(request, response);
    }

    //insert 
    private void insertCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String des = request.getParameter("des");
boolean check = dao.insertCategory(name, des);
        if (!check) {
            request.setAttribute("error", "Category name already exists!");
            request.getRequestDispatcher("/admin/category-add.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/category");
        }

    }

    //update
    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String des = request.getParameter("des");

        dao.updateCategory(id, name, des);
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }

    //delete
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        int id = Integer.parseInt(request.getParameter("id"));

        BookDAO b = new BookDAO();
        int count = b.countBookByCategory(id);
        if (count > 0) {    
            request.setAttribute("error", "Category still contains books!");
            listCategory(request, response); 
            return;
        }
        dao.deleteCategory(id);
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }

}
