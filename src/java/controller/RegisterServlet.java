package controller;

import DAO.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email").trim();

        UserDAO dao = new UserDAO();

        // kiểm tra rỗng
        if (username.isEmpty() || password.isEmpty() || repassword.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "Please fill all fields!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        if (username.isEmpty() || password.isEmpty() || repassword.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "Please fill all fields!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        String usernameRegex = "^[a-zA-Z0-9]{8,21}$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)\\S{8,20}$";

        if (!username.matches(usernameRegex)) {
            request.setAttribute("error", "Username must be 8-20 characters (letters or numbers).");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!password.matches(passwordRegex)) {
            request.setAttribute("error", "Password must be 8-16 characters, contain at least 1 uppercase letter, 1 number, and no spaces.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // check password match
        if (!password.equals(repassword)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // check username tồn tại
        if (dao.checkUser(username)) {
            request.setAttribute("error", "Username already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // insert DB
        boolean success = dao.register(username, password, email);

        if (success) {
            request.setAttribute("success", "Register successful! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Register failed! Try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
