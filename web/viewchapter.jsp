<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<jsp:include page="navbar.jsp"/>

<br>

<div class="reader-nav">

    <!-- PREV -->
    <c:if test="${prev != null}">
        <a class="reader-btn"
           href="${pageContext.request.contextPath}/chapter?chapterID=${prev.chapterID}">
            ◀ Prev
        </a>
    </c:if>

    <!-- LIST -->
    <div class="reader-dropdown">

        <span class="reader-title">
            Danh sách chương ▼
        </span>

        <ul class="reader-list">

    <c:forEach var="c" items="${chapters}">
        <li class="${c.chapterID == chapter.chapterID ? 'current' : ''}">
            <a href="${pageContext.request.contextPath}/chapter?chapterID=${c.chapterID}">
                Chapter ${c.chapterNumber}: ${c.chapTitle}
            </a>
        </li>
    </c:forEach>

</ul>

    </div>

    <!-- NEXT -->
    <c:if test="${next != null}">
        <a class="reader-btn"
           href="${pageContext.request.contextPath}/chapter?chapterID=${next.chapterID}">
            Next ▶
        </a>
    </c:if>

</div>

<br>
<br>

<h2>Chapter ${chapter.chapterNumber}: ${chapter.chapTitle}</h2>

<div style="white-space: pre-line;">
    ${content}
</div>


<div class="reader-nav">

    <!-- PREV -->
    <c:if test="${prev != null}">
        <a class="reader-btn"
           href="${pageContext.request.contextPath}/chapter?chapterID=${prev.chapterID}">
            ◀ Prev
        </a>
    </c:if>

    <!-- LIST -->
    <div class="reader-dropdown">

        <span class="reader-title">
            Danh sách chương ▼
        </span>

        <ul class="reader-list">

    <c:forEach var="c" items="${chapters}">
        <li class="${c.chapterID == chapter.chapterID ? 'current' : ''}">
            <a href="${pageContext.request.contextPath}/chapter?chapterID=${c.chapterID}">
                Chapter ${c.chapterNumber}: ${c.chapTitle}
            </a>
        </li>
    </c:forEach>

</ul>

    </div>

    <!-- NEXT -->
    <c:if test="${next != null}">
        <a class="reader-btn"
           href="${pageContext.request.contextPath}/chapter?chapterID=${next.chapterID}">
            Next ▶
        </a>
    </c:if>

</div>

<script>

const title = document.querySelector(".reader-title");
const list = document.querySelector(".reader-list");

title.addEventListener("click", function(e){
    e.stopPropagation();
    list.classList.toggle("show");

    const current = document.querySelector(".reader-list li.current");

    if(current){
        current.scrollIntoView({
            block: "center"
        });
    }
});

document.addEventListener("click", function(){
    list.classList.remove("show");
});

</script>
