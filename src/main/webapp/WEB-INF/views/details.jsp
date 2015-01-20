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
<div class="apps">
        <div class="app">
            <div class="icon"><img src="${application.detailedImageUrl}" /></div>
            <div class="title">${application.name}</div>
            <div class="description">${application.description}</div>
            <div class="download-btn"><a href="/download/${application.name}">Download</a></div>
        </div>
</div><div class="clear"></div>
<%@ include file="footer.jsp" %>
