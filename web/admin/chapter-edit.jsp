<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Edit Chapter</title>
</head>

<body>

<h2>Edit Chapter</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/admin/chapter" method="post">

<input type="hidden" name="action" value="edit">
<input type="hidden" name="id" value="${chapter.chapterID}">
<input type="hidden" name="bookID" value="${bookID}">

Chapter Number:<br>
<input type="number" name="chapterNumber"
       value="${chapter.chapterNumber}" required>
<br><br>

Chapter Title:<br>
<input type="text" name="ChapTitle"
       value="${chapter.chapTitle}" required>
<br><br>

Content:<br>
<textarea name="content" rows="10" cols="60">
${chapter.content}
</textarea>

<br><br>

<button type="submit">Update Chapter</button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/chapter?action=list&bookID=${bookID}">
Back
</a>

</body>
</html>