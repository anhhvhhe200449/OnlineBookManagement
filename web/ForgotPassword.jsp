<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Forgot Password</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/materialdesignicons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="forgot-page">

<div class="container py-2">
    <div class="row justify-content-center">
        <div class="col-md-4 forgot-form">

            <div class="card">
                <div class="card-header">
                    <h3 class="mb-0">Forgot Password</h3>
                </div>

                <div class="card-body">

                    <form action="${pageContext.request.contextPath}/forgot" method="post">

                        <div class="form-group mb-3">
                            <input class="form-control"
                                   name="username"
                                   placeholder="Username"
                                   required>
                        </div>

                        <div class="form-group mb-3">
                            <input type="email"
                                   class="form-control"
                                   name="email"
                                   placeholder="Email"
                                   required>
                        </div>

                        <p style="color:red">${error}</p>
                        <p style="color:green">${success}</p>

                        <div class="text-center">
                            <button type="submit" class="btn btn-success">
                                Find User
                            </button>
                        </div>

                    </form>

                    <hr>

                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/login">
                            Quay lại đăng nhập
                        </a>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>