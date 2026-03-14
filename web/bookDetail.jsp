<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<jsp:include page="navbar.jsp"/>

<div class="book-detail">

    <!-- LEFT -->
    <div class="book-left">
        <img src="${book.coverImage}" class="detail-cover">

        <c:if test="${not empty chapters}">
        <a href="${pageContext.request.contextPath}/chapter?chapterID=${chapters[0].chapterID}" 
           class="chapter-btn">
           Đọc truyện
        </a>
           </c:if>

        <a href="${pageContext.request.contextPath}/addWishlist?bookID=${book.bookID}" 
           class="chapter-btn">
           Thêm vào Wishlist
        </a>
    </div>

    <!-- RIGHT -->
    <div class="book-right">

        <h2 class="book-title">${book.title}</h2>

        <p><b>Tác giả:</b> ${book.author}</p>

        <p><b>Lượt xem:</b> ${book.views}</p>

        <div class="description">
            ${book.description}
        </div>
        
       <div class="chapter-menu">

    <a href="#" class="chapter-title">
        Danh sách chương ▼
    </a>

    <ul class="chapter-dropdown">

        <c:forEach var="c" items="${chapters}">
            <li>
                <a href="${pageContext.request.contextPath}/chapter?chapterID=${c.chapterID}">
                    Chapter ${c.chapterNumber}: ${c.chapTitle}
                </a>
            </li>
        </c:forEach>

    </ul>

</div>


    </div>
       

</div>