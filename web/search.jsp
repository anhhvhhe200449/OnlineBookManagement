<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">

<a href="${pageContext.request.contextPath}/home" class="home-btn">
    ← Home
</a>
    
<h2 class="section-title">Tìm thấy ${books.size()} kết quả cho "${keyword}"</h2>

<div class="book-list">

    <c:forEach var="b" items="${books}">

        <div class="book-card">

            <img src="${b.coverImage}" width="150">

            <h4>${b.title}</h4>

            <p>Tác giả: ${b.author}</p>

            <a href="book?id=${b.bookID}" class="read-btn">
                Xem truyện
            </a>

        </div>

    </c:forEach>

</div>