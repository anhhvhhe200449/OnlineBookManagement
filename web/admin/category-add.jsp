<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Category</title>
</head>
<body>

    <h2>Add New Category</h2>

    <form action="${pageContext.request.contextPath}/admin/category" method="post">

        <!-- action add -->
        <input type="hidden" name="action" value="add"/>

        <table border="1" cellpadding="8">
            <tr>
                <td>Category Name:</td>
                <td>
                    <input type="text" name="name" required/>
                </td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>
                    <input type="text" name="des"/>
                </td>
            </tr>
        </table>
        <p style="color:red">${error}</p>
        <br>
        <button type="submit">Save</button>
        <a href="${pageContext.request.contextPath}/admin/category">Cancel</a>

    </form>

</body>
</html>