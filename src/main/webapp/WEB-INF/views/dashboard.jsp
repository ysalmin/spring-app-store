<%@ page session="true" %>
<%@ include file="header.jsp" %>
<%@ include file="top.jsp" %>
<h3 class="text-center">Application upload</h3>
<security:authorize ifAnyGranted="ROLE_DEV">
    <div class="app-upload">
    <form:form action="dashboard/upload" id="appUploadForm" modelAttribute="application" enctype="multipart/form-data">
        <div class="row">
            <div class="col-lg-6 center-block">
                <div class="form-group">
                    <input type="text" name="name" placeholder="Application name" class="form-control"/>
                    <form:errors path="name"></form:errors>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 center-block">
                <div class="form-group">
                    <label class="control-label">Application archive</label>
                    <input type="file" name="file"/>
                    <form:errors path="file"></form:errors>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6 center-block">
                <div class="form-group">
                    <textarea name="description" rows="10" cols="40" placeholder="Application description"
                              class="form-control"></textarea>
                    <form:errors path="description"></form:errors>
                </div>
            </div>
        </div>
        <div class="row">
        <div class="col-lg-6 center-block">
            <input type="submit" value="Upload Application" class="btn btn-default"/>
        </div>
    </form:form>
    </div>
</security:authorize>
<security:authorize ifNotGranted="ROLE_DEV">
    <div class="error-message">You have no access to this area. Please
        <a href="<c:url value='j_spring_security_logout' />">login as a developer.</a></div>
</security:authorize>
<%@ include file="footer.jsp" %>