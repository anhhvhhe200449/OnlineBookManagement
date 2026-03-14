package controller;

import DAO.BookDAO;
import DAO.CategoryDAO;
import DAO.ChapterDAO;
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
import model.Chapter;

@WebServlet(name = "ChapterListServlet", urlPatterns = {"/admin/chapter"})
public class ChapterListServlet extends HttpServlet {

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
            out.println("<title>Servlet ChapterListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChapterListServlet at " + request.getContextPath() + "</h1>");
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
    private ChapterDAO ch = new ChapterDAO();

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
                    showAddChapterForm(request, response);
                    break;
                case "view":
                    viewChapter(request, response);
                    break;
                case "edit":
                    showEditChapterForm(request, response);
                    break;
                case "delete":
                    deleteChapter(request, response);
                    break;
                default:
                    listChapter(request, response);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                response.sendRedirect(request.getContextPath() + "/admin/book");
                return;
            }
            switch (action) {
                case "add":
                    insertChapter(request, response);
                    break;
                case "edit":
                    updateChapter(request, response);
                    break;
                case "delete":
                    deleteChapter(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    //form add chapter
    private void showAddChapterForm(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));

        request.setAttribute("bookID", bookID);
        request.getRequestDispatcher("/admin/chapter-add.jsp").forward(request, response);
    }

    //form edit book
    private void showEditChapterForm(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        int bookID = Integer.parseInt(request.getParameter("bookID"));

        int id = Integer.parseInt(request.getParameter("id"));
        Chapter c = ch.getChapterByID(id);

        request.setAttribute("chapter", c);

        request.setAttribute("bookID", bookID);
        request.getRequestDispatcher("/admin/chapter-edit.jsp").forward(request, response);
    }

    //all chapter in book
    private void listChapter(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {
        String bookIDRaw = request.getParameter("bookID");

        if (bookIDRaw == null) {
            response.sendRedirect(request.getContextPath() + "/admin/book");
            return;
        }

        int bookID = Integer.parseInt(bookIDRaw);

        Book book = b.getBookByID(bookID);
        int categoryID = book.getCategoryID();

        List<Chapter> chapterlist = ch.getChapterByBookID(bookID);

        request.setAttribute("chapterlist", chapterlist);
        request.setAttribute("bookID", bookID);
        request.setAttribute("categoryID", categoryID);

        request.getRequestDispatcher("/admin/chapter-list.jsp").forward(request, response);
    }

    //edit chapter
    private void updateChapter(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {

        int id = Integer.parseInt(request.getParameter("id"));  //id chapter
        int bookID = Integer.parseInt(request.getParameter("bookID")); //id của book chứa chapter đó
        int chapterNumber = Integer.parseInt(request.getParameter("chapterNumber"));

        String ChapTitle = request.getParameter("ChapTitle");
        String content = request.getParameter("content");

        boolean check = ch.editChapter(id, bookID, chapterNumber, ChapTitle, content);
        if (!check) {
            request.setAttribute("error", "This chapter can't be edited!");
            request.setAttribute("bookID", bookID);
            request.setAttribute("id", id);
            request.getRequestDispatcher("/admin/chapter-edit.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/chapter?action=list&bookID=" + bookID);
        }

    }

    //view chapter
    private void viewChapter(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {

        int chapterID = Integer.parseInt(request.getParameter("chapterID"));

        Chapter chapter = ch.getChapterByID(chapterID);
        // lấy content
        String content = chapter.getContent();

        // tự chuyển link ảnh thành thẻ img
        content = content.replaceAll(
                       "(https?://\\S+\\.(jpg|jpeg|png|gif|webp))",
                       "<div style='text-align:center'><img src='$1' style='max-width:600px'></div>"
        );

        request.setAttribute("chapter", chapter);
        request.setAttribute("content", content);

        request.getRequestDispatcher("/admin/chapter-view.jsp").forward(request, response);
    }

    //add chapter
    private void insertChapter(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {

        int bookID = Integer.parseInt(request.getParameter("bookID")); //id của book chứa chapter đó
        int chapterNumber = Integer.parseInt(request.getParameter("chapterNumber"));

        String ChapTitle = request.getParameter("ChapTitle");
        String content = request.getParameter("content");
        boolean check = ch.insertChapter(bookID, chapterNumber, ChapTitle, content);
        if (!check) {
            request.setAttribute("error", "This chapter already exists!");
            request.setAttribute("bookID", bookID);
            request.getRequestDispatcher("/admin/chapter-add.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/chapter?action=list&bookID=" + bookID);
        }

    }

    //delete chapter
    private void deleteChapter(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException, SQLException {

        int id = Integer.parseInt(request.getParameter("id"));
        int bookID = Integer.parseInt(request.getParameter("bookID")); //id của book chứa chapter đó

        ch.deleteChapter(id);
        response.sendRedirect(request.getContextPath() + "/admin/chapter?action=list&bookID=" + bookID);
    }

}
