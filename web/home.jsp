<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>

        <title>Home</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <script>
            function toggleMenu() {
                let menu = document.getElementById("menu");
                menu.style.display = (menu.style.display === "block") ? "none" : "block";
            }
            function toggleCategory(e) {
                e.preventDefault(); // không reload trang

                let menu = document.getElementById("categoryDropdown");

                if (menu.style.display === "block") {
                    menu.style.display = "none";
                } else {
                    menu.style.display = "block";
                }
            }
        </script>

    </head>

    <body>

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
                                class="avatar"
                                />

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


        <div class="search-bar">

            <form action="${pageContext.request.contextPath}/search" method="get">

                <input
                    type="text"
                    name="keyword"
                    placeholder="Tìm kiếm tác giả, truyện..."
                    >

                <button type="submit">
                    Tìm kiếm
                </button>

            </form>

        </div>


        <h2 class="section-title">
            TOP 10 TRUYỆN CÓ LƯỢT VIEWS CAO NHẤT
        </h2>

        <div class="book-list">

            <c:forEach var="b" items="${books}">

                <div class="book-card">

                    <img class="book-img"
                         src="${b.coverImage}"
                         alt="${b.title}">

                    <h4>${b.title}</h4>

                    <a class="read-btn"
                       href="${pageContext.request.contextPath}/book?id=${b.bookID}">
                        Xem truyện
                    </a>
                    <div class="views">
                        👁 ${b.views} lượt xem
                    </div>
                </div>

            </c:forEach>

        </div>


        <div class="pagination-dots">

            <c:forEach begin="1" end="${totalPages}" var="i">

                <a href="${pageContext.request.contextPath}/home?page=${i}"
                   class="dot ${i == currentPage ? 'active' : ''}">
                </a>

            </c:forEach>

        </div>
        <h2 class="section-title">
            SÁCH MỚI CẬP NHẬT
        </h2>

        <div class="book-list">

            <c:forEach var="b" items="${latestBooks}">

                <div class="book-card">

                    <img class="book-img"
                         src="${b.coverImage}"
                         alt="${b.title}">

                    <h4>${b.title}</h4>

                    <a class="read-btn"
                       href="${pageContext.request.contextPath}/book?id=${b.bookID}">
                        Xem truyện
                    </a>

                </div>

            </c:forEach>

        </div>

        <div class="pagination">

            <!-- Prev -->
            <c:if test="${latestPage > 1}">
                <a href="home?latestPage=${latestPage-1}">&lt;</a>
            </c:if>

            <!-- Nếu tổng trang <= 8 -->
            <c:if test="${latestTotalPages <= 8}">
                <c:forEach begin="1" end="${latestTotalPages}" var="i">
                    <a href="home?latestPage=${i}" 
                       class="${i==latestPage?'active':''}">
                        ${i}
                    </a>
                </c:forEach>
            </c:if>

            <!-- Nếu tổng trang > 8 -->
            <c:if test="${latestTotalPages > 8}">

                <!-- first pages -->
                <c:forEach begin="1" end="8" var="i">
                    <a href="home?latestPage=${i}" 
                       class="${i==latestPage?'active':''}">
                        ${i}
                    </a>
                </c:forEach>

                <span class="dots">...</span>

                <!-- last pages -->
                <c:forEach begin="${latestTotalPages-1}" end="${latestTotalPages}" var="i">
                    <a href="home?latestPage=${i}">
                        ${i}
                    </a>
                </c:forEach>

            </c:if>

            <!-- Next -->
            <c:if test="${latestPage < latestTotalPages}">
                <a href="home?latestPage=${latestPage+1}">&gt;</a>
            </c:if>

        </div>

    </body>
</html>