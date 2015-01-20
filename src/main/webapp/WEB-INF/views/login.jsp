<%@ page session="true" %>
<%@ include file="header.jsp" %>
<%@ include file="top.jsp" %>
<h3 class="text-center">Please enter your credantials</h3>

<form method="post" class="signin" action="j_spring_security_check">
    <div class="login-form">
        <div class="row">
            <div class="col-lg-3 center-block">
                <div class="form-group">
                    <input id="login" name="j_username" placeholder="Login" class="form-control"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3 center-block">
                <div class="form-group"><input id="pass" name="j_password" type="password" placeholder="Password"
                                               class="form-control"/></div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3 center-block">
                <input type="submit" value="Login" id="sbmt-pin" name="commit" class="btn btn-default"/>
            </div>
        </div>
    </div>
</form>
<%@ include file="footer.jsp" %>
