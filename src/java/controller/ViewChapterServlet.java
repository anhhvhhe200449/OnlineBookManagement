/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chapter;
import DAO.ChapterDAO;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ViewChapterServlet", urlPatterns={"/chapter"})
public class ViewChapterServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ViewChapterServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewChapterServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private ChapterDAO ch = new ChapterDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         int chapterID = Integer.parseInt(request.getParameter("chapterID"));

    Chapter chapter = ch.getChapterByID(chapterID);

    String content = chapter.getContent();

    content = content.replaceAll(
        "(https?://\\S+\\.(jpg|jpeg|png|gif|webp))",
        "<div style='text-align:center'><img src='$1' style='max-width:600px'></div>"
    );

    int bookID = chapter.getBookID();
    int chapterNumber = chapter.getChapterNumber();

    // danh sách chương
    List<Chapter> chapters = ch.getChapterByBookID(bookID);

    // prev / next
    Chapter prev = ch.getPrev(chapterNumber, bookID);
    Chapter next = ch.getNext(chapterNumber, bookID);

    request.setAttribute("chapter", chapter);
    request.setAttribute("content", content);
    request.setAttribute("chapters", chapters);
    request.setAttribute("prev", prev);
    request.setAttribute("next", next);

    request.getRequestDispatcher("viewchapter.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
