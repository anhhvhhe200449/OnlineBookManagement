<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Book"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Book List</title>
    </head>
    <body>

        <h2>Book List</h2>

        <a href="${pageContext.request.contextPath}/admin/book?action=add&categoryID=${param.categoryID}">
            + Add Book
        </a>

        <br><br>

        <%
            List<Book> list = (List<Book>) request.getAttribute("booklist");

            if(list != null && !list.isEmpty()){
        %>

        <table border="1" cellpadding="8">

            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Description</th>
                <th>Cover</th>
                <th>Action</th>
            </tr>

            <%
                for(Book b : list){
            %>

            <tr>

                <td><%= b.getBookID() %></td>

                <td><%= b.getTitle() %></td>

                <td><%= b.getAuthor() %></td>

                <td><%= b.getDescription() %></td>

                <td>
                    <img src="<%= b.getCoverImage() %>" width="80">
                </td>

                <td>

                    <a href="${pageContext.request.contextPath}/admin/book?action=edit&id=<%= b.getBookID() %>&categoryID=${param.categoryID}">
                        Edit
                    </a>

                    </br>

                    <a href="${pageContext.request.contextPath}/admin/book?action=delete&id=<%= b.getBookID() %>&categoryID=${param.categoryID}"
                       onclick="return confirm('Are you sure to delete this book?');">
                        Delete
                    </a>

                    <a href="${pageContext.request.contextPath}/admin/chapter?bookID=<%= b.getBookID() %>">
                        Manage Chapters
                    </a>

                </td>

            </tr>

            <%
                }
            %>

        </table>

        <%
            }else{
        %>

        <p>No books found.</p>

        <%
            }
        %>

        <br>

        <a href="${pageContext.request.contextPath}/admin/category">
            Back to Category
        </a>

    </body>
</html>