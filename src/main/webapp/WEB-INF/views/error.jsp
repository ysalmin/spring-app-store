<%@ page session="false" %>
<%@ include file="header.jsp" %>
<%@ include file="top.jsp" %>
<div class="alert alert-danger" role="alert">
   ${title}. <spring:message code="${errorMsg}" />
</div>
<%@ include file="footer.jsp" %>