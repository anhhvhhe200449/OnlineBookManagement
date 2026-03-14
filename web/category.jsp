<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Books</title>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>

        <c:choose>

            <c:when test="${not empty books}">
                <a href="${pageContext.request.contextPath}/home" class="home-btn">
                    ← Home
                </a>
                <h2 class="section-title">Các truyện của thể loại ${categoryName}</h2>

                <div class="book-list">

                    <c:forEach var="b" items="${books}">

                        <div class="book-card">

                            <img class="book-img" src="${b.coverImage}">

                            <h4>${b.title}</h4>

                            <p>${b.author}</p>

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

            </c:when>

            <c:otherwise>

                <h3>Chưa có sách trong thể loại này</h3>

            </c:otherwise>

        </c:choose>

    </body>
</html>