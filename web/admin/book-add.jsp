<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Book</title>
</head>
<body>

<h2>Add New Book</h2>

<%
    String error = (String) request.getAttribute("error");
    if(error != null){
%>
<p style="color:red"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/admin/book" method="post">

    <input type="hidden" name="action" value="add">


    <input type="hidden" name="id" value="${categoryID}">

    Title:
    <br>
    <input type="text" name="title" required>
    <br><br>

    Description:
    <br>
    <textarea name="des" rows="4" cols="40"></textarea>
    <br><br>

    Author:
    <br>
    <input type="text" name="author">
    <br><br>

    Cover Image URL:
    <br>
    <input type="text" name="coverImage">
    <br><br>

    <button type="submit">Add Book</button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/book?action=list&categoryID=${categoryID}">
    Back to Book List
</a>

</body>
</html>
