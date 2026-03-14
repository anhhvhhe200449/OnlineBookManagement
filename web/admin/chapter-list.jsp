<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Chapter List</title>
</head>

<body>

<h2>Chapter List</h2>

<a href="${pageContext.request.contextPath}/admin/book?action=list&categoryID=${categoryID}">
Back to Book List
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/chapter?action=add&bookID=${bookID}">
    Add Chapter
</a>

<br><br>

<table border="1" cellpadding="8">

<tr>
    <th>ID</th>
    <th>Chapter Number</th>
    <th>Title</th>
    <th>Action</th>
</tr>

<c:forEach var="c" items="${chapterlist}">

<tr>

<td>${c.chapterID}</td>

<td>${c.chapterNumber}</td>

<td>${c.chapTitle}</td>

<td>
    
    <a href="${pageContext.request.contextPath}/admin/chapter?action=view&chapterID=${c.chapterID}">
        View
    </a>
        
|        

<a href="${pageContext.request.contextPath}/admin/chapter?action=edit&id=${c.chapterID}&bookID=${bookID}">
    Edit
</a>

|

<a href="${pageContext.request.contextPath}/admin/chapter?action=delete&id=${c.chapterID}&bookID=${bookID}"
   onclick="return confirm('Delete this chapter?');">
    Delete
</a>

</td>

</tr>

</c:forEach>

</table>

</body>
</html>