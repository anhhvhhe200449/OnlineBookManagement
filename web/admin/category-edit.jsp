<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Category</title>
</head>
<body>

    <h2>Edit Category</h2>

    <form action="${pageContext.request.contextPath}/admin/category" method="post">

        <!-- action edit -->
        <input type="hidden" name="action" value="edit"/>
        <input type="hidden" name="id" value="${category.categoryID}"/>

        <table border="1" cellpadding="8">
            <tr>
                <td>Category Name:</td>
                <td>
                    <input type="text" name="name" value="${category.categoryName}" required/>
                </td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>
                    <input type="text" name="des" value="${category.description}"/>
                </td>
            </tr>
        </table>

        <br>
        <button type="submit">Save</button>
        <a href="${pageContext.request.contextPath}/admin/category">Cancel</a>

    </form>

</body>
</html>