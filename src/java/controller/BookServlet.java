package controller;

import DAO.BookDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Book;
import model.User;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // chưa login -> chuyển sang login
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        // lưu các truyện đã xem
        Map<Integer, Long> viewedBooks
                       = (Map<Integer, Long>) session.getAttribute("viewedBooks");

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

        session.setAttribute("viewedBooks", viewedBooks);

        // lấy thông tin truyện
        Book b = dao.getBookByID(id);

        request.setAttribute("book", b);

        request.getRequestDispatcher("bookDetail.jsp").forward(request, response);
    }
}
