<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="u" value="${sessionScope.user}" />

<div class="navbar">


    <div class="logo">
        📚 OnBook
    </div>

    <ul class="nav-menu">

        <li>
            <a href="${pageContext.request.contextPath}/home">
                Trang chủ
            </a>
        </li>

        <li class="category-menu">
            <a href="#" onclick="toggleCategory(event)">Thể loại</a>

            <ul id="categoryDropdown" class="dropdown">
                <c:forEach var="c" items="${list}">
                    <li>
                        <a href="${pageContext.request.contextPath}/category?id=${c.categoryID}">
                            ${c.categoryName}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/wishlist">
                MyWishlist
            </a>
        </li>

    </ul>

    <c:choose>

        <c:when test="${sessionScope.user == null}">

            <div class="auth-buttons">

                <a class="login-btn"
                   href="${pageContext.request.contextPath}/login">
                    <i class="fa-solid fa-user"></i> Đăng nhập
                </a>

                <a class="register-btn"
                   href="${pageContext.request.contextPath}/register">
                    <i class="fa-solid fa-pen-to-square"></i> Đăng ký
                </a>

            </div>

        </c:when>

        <c:otherwise>

            <div class="avatar-menu">

                <div class="user-info" onclick="toggleMenu()">

                    <img
                        src="${pageContext.request.contextPath}/images/avatars/${empty u.avatar ? 'default.png' : u.avatar}"
                        class="avatar"/>

                    <span class="username">
                        ${u.username}
                    </span>

                </div>

                <div id="menu" class="dropdown-menu">

                    <a href="${pageContext.request.contextPath}/profile">
                        Edit Profile
                    </a>

                    <a href="${pageContext.request.contextPath}/logout">
                        Logout
                    </a>

                </div>

            </div>

        </c:otherwise>

    </c:choose>

</div>

<script>
    function toggleMenu() {
        const menu = document.getElementById("menu");
        menu.classList.toggle("show");
    }
</script>