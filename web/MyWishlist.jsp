<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<a href="${pageContext.request.contextPath}/home" class="home-btn">
    ← Home
</a>
<br>
<h2 class="section-title">My Wishlist</h2>

<div class="wishlist-list">

    <c:forEach var="b" items="${wishlist}">

        <div class="book-card">

            <img class="book-img"
                 src="${b.coverImage}"
                 alt="${b.title}">

            <h4>${b.title}</h4>

            <p>Tác giả: ${b.author}</p>

            <a class="btn read-btn"
               href="book?id=${b.bookID}">
                Xem truyện
            </a>

            <a class="btn remove-btn"
               href="removeWishlist?id=${b.bookID}">
                Remove
            </a>
        </div>

    </c:forEach>

</div>