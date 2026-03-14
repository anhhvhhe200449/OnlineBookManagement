package controller;

import DAO.BookDAO;
import DAO.CategoryDAO;
import DAO.ChapterDAO;
import DAO.BookDAO;
import DAO.CategoryDAO;
import DAO.ChapterDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;
import model.Chapter;

@WebServlet(name="BookServlet", urlPatterns={"/book"})

public class BookServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            
            // chưa login thì không cho xem và không tăng view
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            int id = Integer.parseInt(request.getParameter("id"));
            
            
            
            // lưu các truyện đã xem
            Map<Integer, Long> viewedBooks =
                    (Map<Integer, Long>) session.getAttribute("viewedBooks");
            
            if (viewedBooks == null) {
                viewedBooks = new HashMap<>();
            }
            
            long now = System.currentTimeMillis();
            
            BookDAO dao = new BookDAO();
            
            // nếu chưa xem hoặc đã quá 10 phút
            if (!viewedBooks.containsKey(id) || now - viewedBooks.get(id) > 600000) {
                
                dao.increaseView(id);
                
                viewedBooks.put(id, now);
            }
            
            CategoryDAO cd = new CategoryDAO();
            request.setAttribute("list", cd.getAll());
            
            session.setAttribute("viewedBooks", viewedBooks);
            
            // lấy thông tin truyện
            Book b = dao.getBookByID(id);
            
            request.setAttribute("book", b);
            
            //lấy chapter
            ChapterDAO c = new ChapterDAO();
            List<Chapter> list = c.getChapterByBookID(id);
            request.setAttribute("chapters", list); 
            
            request.getRequestDispatcher("bookDetail.jsp").forward(request, response);
            
            
                    
        } catch (SQLException ex) {
            Logger.getLogger(BookServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
