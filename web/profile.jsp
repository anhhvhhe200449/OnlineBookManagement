<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Profile</title>
        
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body class="profile-page">
        <a href="${pageContext.request.contextPath}/home" class="home-btn">
            ← Home
        </a>

        <div class="profile-container">

            <div class="card">

                <div class="card-header text-center">
                    <h3>Profile</h3>
                </div>

                <div class="card-body text-center">

                    <!-- SUCCESS MESSAGE -->
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            ${success}
                        </div>
                        <c:remove var="success" scope="session"/>
                    </c:if>

                    <!-- ERROR MESSAGE -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                        <c:remove var="error" scope="session"/>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/updateProfile"
                          method="post"
                          enctype="multipart/form-data">

                        <!-- AVATAR -->
                        <div class="profile-avatar">

                            <img src="${pageContext.request.contextPath}/images/avatars/${empty sessionScope.user.avatar ? 'default.png' : sessionScope.user.avatar}?v=<%=System.currentTimeMillis()%>"
                                 class="avatar-img">

                            <input type="file"
                                   name="avatarFile"
                                   class="form-control mt-2"
                                   accept="image/*">

                        </div>

                        <hr>

                        <!-- USERNAME -->
                        <div class="form-group mb-3">

                            <input id="username"
                                   name="username"
                                   class="form-control"
                                   value="<%=u.getUsername()%>"
                                   pattern="[A-Za-z0-9]{8,20}"
                                   title="Username must be 8-20 characters"
                                   readonly>

                            <button type="button"
                                    class="btn btn-sm btn-outline-secondary mt-2"
                                    onclick="enableEdit('username')">
                                Edit Username
                            </button>

                        </div>

                        <!-- PASSWORD -->
                        <div class="form-group mb-3">

                            <input id="password"
                                   type="password"
                                   name="password"
                                   class="form-control"
                                   placeholder="********"
                                   pattern="(?=.*[A-Z])(?=.*\d)\S{8,16}"
                                   title="Password must be 8-16 characters, contain uppercase letter and number"
                                   readonly>

                            <button type="button"
                                    class="btn btn-sm btn-outline-secondary mt-2"
                                    onclick="enablePasswordEdit()">
                                Edit Password
                            </button>

                        </div>

                        <!-- EMAIL -->
                        <div class="form-group mb-3">

                            <input id="email"
                                   name="email"
                                   class="form-control"
                                   value="<%=u.getEmail()%>"
                                   readonly>

                            <button type="button"
                                    class="btn btn-sm btn-outline-secondary mt-2"
                                    onclick="enableEdit('email')">
                                Edit Email
                            </button>

                        </div>

                        <button type="submit"
                                class="btn btn-success mt-3 w-100">
                            Save Changes
                        </button>

                    </form>

                </div>

            </div>

        </div>

        <script>
            function enableEdit(id) {
                let input = document.getElementById(id);
                input.removeAttribute("readonly");
                input.focus();
            }

            function enablePasswordEdit() {
                let input = document.getElementById("password");
                input.removeAttribute("readonly");
                input.value = "";
                input.placeholder = "Enter new password";
                input.focus();
            }
        </script>

        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>