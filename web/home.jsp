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

        <jsp:include page="navbar.jsp"/>



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

                    <a href="${pageContext.request.contextPath}/book?id=${b.bookID}">
                        <img class="book-img"
                             src="${b.coverImage}"
                             alt="${b.title}"
                             >
                    </a>


                    <a class ="book-inhome" href="${pageContext.request.contextPath}/book?id=${b.bookID}">
                        ${b.title}
                    </a>


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

    </body>
</html>