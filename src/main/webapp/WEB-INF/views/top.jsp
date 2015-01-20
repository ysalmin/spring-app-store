<security:authentication var="user" property="principal" />
<h1 class="text-center">Welcome to App Shopping Cart</h1>
<div class="top-panel">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <security:authorize ifAnyGranted="ROLE_DEV,ROLE_USER">
                   <div class="navbar-brand"> ${user.username}</div>
                </security:authorize>
                <c:if test="${not fn:containsIgnoreCase(requestScope['javax.servlet.forward.request_uri'], '/login')}">
                    <security:authorize ifNotGranted="ROLE_DEV,ROLE_USER">
                        <a class="navbar-brand" href="/dashboard/login">Login</a>
                    </security:authorize>
                </c:if>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/'}">
                        <li><a href="/">Home</a></li>
                    </c:if>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <security:authorize ifAnyGranted="ROLE_DEV">
                        <c:if test="${not fn:containsIgnoreCase(requestScope['javax.servlet.forward.request_uri'], '/dashboard')}">
                            <li><a href="/dashboard">Dashboard</a></li>
                        </c:if>
                    </security:authorize>
                    <security:authorize ifAnyGranted="ROLE_DEV,ROLE_USER">
                        <li><a href="<c:url value="j_spring_security_logout" />">Log out</a></li>
                    </security:authorize>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
<div class="clear"></div>
