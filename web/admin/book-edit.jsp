<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Book</title>
</head>
<body>

    <h2>Edit Book</h2>

    <form action="${pageContext.request.contextPath}/admin/book" method="post">

        <!-- action edit -->
        <input type="hidden" name="action" value="edit"/>
        <input type="hidden" name="id" value="${book.bookID}"/>
        <input type="hidden" name="categoryID" value="${book.categoryID}"/>

        <table border="1" cellpadding="8">
            <tr>
                <td>Title</td>
                <td>
                    <input type="text" name="title" value="${book.title}" required/>
                </td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>
                    <input type="text" name="des" value="${book.description}"/>
                </td>
            </tr>
            <tr>
                <td>Cover Image:</td>
                <td>
                    <input type="text" name="coverImage" value="${book.coverImage}"/>
                </td>
            </tr>
            <tr>
                <td>Author:</td>
                <td>
                    <input type="text" name="author" value="${book.author}"/>
                </td>
            </tr>
        </table>

        <br>
        <button type="submit">Save</button>
        <a href="${pageContext.request.contextPath}/admin/book?action=list&categoryID=${book.categoryID}">Cancel</a>

    </form>

</body>
</html>