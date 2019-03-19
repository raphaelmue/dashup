<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" scope="session" />
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp" />
    <body >
        <header class="develop-toolbar mdc-toolbar mdc-toolbar--platform">
            <nav>
                <div class="nav-wrapper">
                    <a href="#" class="brand-logo" style="font-size: 20px;">D A S H U P</a>
                </div>
            </nav>
        </header>
        <div class="container">
            <div class="row">
                <div class="col m6 offset-m3 s10 offset-s1">
                    <h3><fmt:message key="i18n.login" /></h3>
                    <p><a href="${pageContext.request.contextPath}/register"><fmt:message key="i18n.notRegisteredYet" /></a></p>
                </div>
            </div>
            <form action="${pageContext.request.contextPath}/handleLogin" method="POST">
                <div class="row">
                    <div class="col m6 offset-m3 s10 offset-s1">
                        <div class="input-field">
                            <input id="text_field_login_email" name="email" type="text" class="validate">
                            <label for="text_field_login_email"><fmt:message key="i18n.emailAddress" /></label>
                        </div>
                    </div>
                    <div class="col m6 offset-m3 s10 offset-s1">
                        <div class="input-field">
                            <input id="text_field_login_password" name="password" type="password" class="validate">
                            <label for="text_field_login_password"><fmt:message key="i18n.password" /></label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col m6 offset-m3 s10 offset-s1">
                        <label >
                            <input type="checkbox" name="rememberMe" class="filled-in" checked="checked" />
                            <span><fmt:message key="i18n.rememberMe" /></span>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="col m6 offset-m3 s10 offset-s1">
                        <button class="btn waves-effect waves-light" type="submit" name="action">
                            <fmt:message key="i18n.login" />
                            <i class="fas fa-sign-in-alt"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </body>

    <script>
        let toastOptions = {};
        switch (getAnchor()) {
            case "invalidCredentials":
                toastOptions = {
                    html: "<fmt:message key="i18n.errorInvalidCredentials" />",
                    classes: "error"
                };
                break;
        }
        if (getAnchor() !== null && getAnchor() !== "") {
            M.toast(toastOptions);
            clearAnchor()
        }
    </script>
</html>
