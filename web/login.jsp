<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/materialdesignicons.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<body class="login-page">

<div class="login-form auth-form">

    <div class="card">

        <div class="card-header">
            <h3>Login</h3>
        </div>

        <div class="card-body">

            <!-- FORM LOGIN -->
            <form action="${pageContext.request.contextPath}/login" method="post" autocomplete="off">

                <div class="form-group">
                    <input class="form-control"
                           name="username"
                           placeholder="Username"
                           type="text"
                           required>
                </div>

                <div class="form-group">
                    <input type="password"
                           class="form-control"
                           name="password"
                           placeholder="Password"
                           required>
                </div>

                <p class="text-danger text-center">${error}</p>

                <button type="submit" class="btn btn-success w-100">
                    Login
                </button>

            </form>
            <!-- END FORM -->

            <hr>

            <div class="text-center">
                <span>Chưa có tài khoản?</span>
                <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
            </div>

            <div class="text-center mt-2">
                <a href="${pageContext.request.contextPath}/forgot">Quên mật khẩu?</a>
            </div>

        </div>
    </div>

</div>

<script src="js/bootstrap.min.js"></script>

</body>
</html>