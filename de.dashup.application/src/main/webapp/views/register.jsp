<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<html>
    <head>
        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />
        <link  rel="stylesheet" type="text/css" href="./../styles/login.css" />

        <title>dashup - <fmt:message key="i18n.registering" /></title>
    </head>
    <body>
        <div class="jumbotron jumbotron-fluid bg-primary text-white">
            <div class="container">
                <h1 class="display-4">dashup</h1>
                <p class="lead"><fmt:message key="i18n.dashupDescription" /></p>
            </div>
        </div>

        <br />
        <br />

        <div class="container col-lg-4 col-lg-offset-4">
            <div class="error-box">${errorMessage}</div>
            <form action="${pageContext.request.contextPath}/entry/handleRegisterUser" method="POST">
                <div class="form-group">
                    <label class="text-white" for="email"><fmt:message key="i18n.emailAddress" /></label>
                    <input type="text" class="form-control" id="email" name="email" aria-describedby="emailHelp"
                           placeholder="<fmt:message key="i18n.emailAddress" />">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label class="text-white" for="email"><fmt:message key="i18n.name" /></label>
                    <input type="text" class="form-control" id="name" name="name"
                           placeholder="<fmt:message key="i18n.name" />">
                </div>
                <div class="form-group">
                    <label class="text-white" for="email"><fmt:message key="i18n.surname" /></label>
                    <input type="text" class="form-control" id="surname" name="surname"
                           placeholder="<fmt:message key="i18n.surname" />">
                </div>
                <div class="form-group">
                    <label class="text-white" for="email"><fmt:message key="i18n.password" /></label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="<fmt:message key="i18n.password" />">
                </div>
                <div class="form-group">
                    <label class="text-white" for="password"><fmt:message key="i18n.repeatPassword" /></label>
                    <input type="password" class="form-control" id="repeat-password" name="repeat-password"
                           placeholder="<fmt:message key="i18n.repeatPassword" />">
                </div>
                <button type="submit" class="btn btn-primary" id="submit_button"><fmt:message key="i18n.submit" /></button>
            </form>
        </div>

        <br />

        <div class="container col-lg-4 col-lg-offset-4"><div>
            <label class="text-white" for="google"><fmt:message key="i18n.signInWithGoogle" /></label>
            <div id="google" class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
        </div>

        <jsp:include page="./includes/bodyInclude.jsp" />
        <script src="../libraries/login.js"></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
    </body>
</html>
