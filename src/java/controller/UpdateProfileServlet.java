package controller;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import model.User;

@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/updateProfile"})
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User u = (User) session.getAttribute("user");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // ===== USERNAME VALIDATION =====
        if (username != null && !username.trim().isEmpty()) {

            String usernameRegex = "^[a-zA-Z0-9]{8,20}$";

            if (!username.matches(usernameRegex)) {
                request.setAttribute("error",
                               "Username must be 8-20 characters (letters or numbers)");
                request.getRequestDispatcher("profile.jsp")
                               .forward(request, response);
                return;
            }

            u.setUsername(username);
        }

        // ===== PASSWORD VALIDATION =====
        if (password != null && !password.trim().isEmpty()) {

            String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)\\S{8,16}$";

            if (!password.matches(passwordRegex)) {
                request.setAttribute("error",
                               "Password must be 8-16 characters, contain at least 1 uppercase letter and 1 number, no spaces.");
                request.getRequestDispatcher("profile.jsp")
                               .forward(request, response);
                return;
            }

            u.setPassword(password);
        }

        // ===== EMAIL =====
        if (email != null && !email.trim().isEmpty()) {
            u.setEmail(email);
        }

        // ===== UPLOAD AVATAR =====
        Part filePart = request.getPart("avatarFile");

        if (filePart != null && filePart.getSize() > 0) {

            String contentType = filePart.getContentType();

            if (!contentType.startsWith("image/")) {
                request.setAttribute("error", "Only image files are allowed!");
                request.getRequestDispatcher("profile.jsp")
                               .forward(request, response);
                return;
            }

            String fileName = filePart.getSubmittedFileName();

            String newFileName = System.currentTimeMillis() + "_" + fileName;

            String uploadPath = getServletContext()
                           .getRealPath("/images/avatars");

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            filePart.write(uploadPath + File.separator + newFileName);

            u.setAvatar(newFileName);
        }

        // ===== UPDATE DATABASE =====
        UserDAO dao = new UserDAO();
        dao.update(u);

        // ===== UPDATE SESSION =====
        session.setAttribute("user", u);
        // success message
        session.setAttribute("success", "Profile updated successfully!");
        // ===== REDIRECT HOME =====
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
