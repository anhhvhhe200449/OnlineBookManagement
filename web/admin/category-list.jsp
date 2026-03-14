<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>All Category</title>
    </head>
    <body>

        <h2>Category Management</h2>

        <!-- Error when delete category if exist book -->
    <c:if test="${error != null}">
        <p style="color:red">${error}</p>
    </c:if>

    <!-- Add Category -->
    <p>
        <a href="${pageContext.request.contextPath}/admin/category?action=add">
            + Add Category
        </a>
    </p>

    <%
        List<Category> list = (List<Category>) request.getAttribute("list");
        if (list != null && !list.isEmpty()) {
    %>

    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Action</th>
        </tr>

        <%
            for (Category c : list) {
        %>
        <tr>
            <td><%= c.getCategoryID() %></td>
            <td><%= c.getCategoryName() %></td>
            <td><%= c.getDescription() %></td>
            <td>
                <!-- Edit -->
                <a href="${pageContext.request.contextPath}/admin/category?action=edit&id=<%= c.getCategoryID() %>">
                    Edit
                </a>

                
                    
                    <br>

                    <!-- Add Book -->
                    <a href="${pageContext.request.contextPath}/admin/book?action=add&categoryID=<%= c.getCategoryID() %>">
                        Add Book
                    </a>

                    <br>

                    <!-- View Books -->
                    <a href="${pageContext.request.contextPath}/admin/book?action=list&categoryID=<%= c.getCategoryID() %>">
                        View Books
                    </a>
                        
                        
                        <!-- Delete -->
                <form action="${pageContext.request.contextPath}/admin/category"
                      method="post"
                      style="display:inline;"
                      onsubmit="return confirm('Are you sure you want to delete?');">

                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="id" value="<%= c.getCategoryID() %>"/>

                    <button type="submit">Delete</button>
                </form>
                        
            </td>

        </tr>
        <%
            }
        %>

    </table>

    <%
        } else {
    %>
    <p>No categories found.</p>
    <%
        }
    %>

    <br>
    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp">
        Back to Dashboard
    </a>

</body>
</html>