<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Add Chapter</title>
</head>

<body>

<h2>Add Chapter</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/admin/chapter" method="post">

<input type="hidden" name="action" value="add">
<input type="hidden" name="bookID" value="${bookID}">

Chapter Number:<br>
<input type="number" name="chapterNumber" required>
<br><br>

Chapter Title:<br>
<input type="text" name="ChapTitle" required>
<br><br>

Content:<br>
<textarea name="content" rows="10" cols="60"></textarea>
<br><br>

<button type="submit">Add Chapter</button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/chapter?action=list&bookID=${bookID}">
Back
</a>

</body>
</html>