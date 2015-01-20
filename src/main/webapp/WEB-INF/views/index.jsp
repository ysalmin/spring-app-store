<%@ page session="true" %>
<%@ include file="header.jsp" %>
<%@ include file="top.jsp" %>
<div class="popular-apps">
    <c:forEach items="${mostPopularApps}" var="application" varStatus="status">
        <div class="app">
            <div class="icon"><a href="/details/${application.name}"><img src="${application.previewImageUrl}" /></a></div>
        </div>
    </c:forEach>
</div>
<div class="clear"></div>
<div class="categories">
<ul>
    <c:forEach items="${categories}" var="category" varStatus="status">
       <li><a href="/category/${category.name}">${category.name}</a></li>
    </c:forEach>
</ul>
</div>
<div class="apps">
<c:forEach items="${applications}" var="application" varStatus="status">
    <div class="app">
        <div class="icon"><a href="/details/${application.name}"><img src="${application.previewImageUrl}" /></a></div>
        <div class="title"><a href="/details/${application.name}">${application.name}</a></div>
    </div>
</c:forEach>
</div>
<div class="clear"></div>
<%@ include file="footer.jsp" %>
