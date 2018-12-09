<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <head>
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id"
              content="974420842298-kd1mc8a01ij835o0upmgq41fnc2vf5kp.apps.googleusercontent.com">
        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />
        <link  rel="stylesheet" type="text/css" href="./../styles/login.css" />

        <title><fmt:message key="i18n.login" /></title>
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
            <form action="${pageContext.request.contextPath}/entry/handleLogin" method="POST">
                <div class="form-group">
                    <label class="text-white" for="email"><fmt:message key="i18n.emailAddress" /></label>
                    <input type="text" class="form-control" id="email" name="email" aria-describedby="emailHelp"
                           placeholder="<fmt:message key="i18n.emailAddress" />">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label class="text-white" for="password"><fmt:message key="i18n.password" /></label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="<fmt:message key="i18n.password" />">
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                    <label class="text-white form-check-label" for="rememberMe"><fmt:message key="i18n.rememberMe" /></label>
                </div>
                <div style="margin: 10px 0"><a href="${pageContext.request.contextPath}/entry/register"><fmt:message key="i18n.notRegisteredYet"/></a></div>
                <button type="submit" class="btn btn-primary" id="submit_button"><fmt:message key="i18n.submit" /></button>
            </form>
        </div>

        <br />

        <div class="container col-lg-4 col-lg-offset-4"><div>
            <label class="text-white" for="google"><fmt:message key="i18n.signInWithGoogle" /></label>
            <div id="google" class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
        </div>

        <jsp:include page="./includes/bodyInclude.jsp" />
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script>

            //gapi.load('auth2', function () {
            //  gapi.auth2.init();
            //});

            function onSignIn(googleUser) {
                // Useful data for your client-side scripts:
                var profile = googleUser.getBasicProfile();

                console.log("ID: " + profile.getId()); // Don't send this directly to your server!
                console.log('Full Name: ' + profile.getName());
                console.log('Given Name: ' + profile.getGivenName());
                console.log('Family Name: ' + profile.getFamilyName());
                console.log("Image URL: " + profile.getImageUrl());
                console.log("Email: " + profile.getEmail());

                // The ID token you need to pass to your backend:
                var id_token = googleUser.getAuthResponse().id_token;
                console.log("ID Token: " + id_token);
            };

        </script>
    </body>
</html>
