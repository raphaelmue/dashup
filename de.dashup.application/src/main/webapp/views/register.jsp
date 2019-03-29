<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n" scope="session"/>
<html lang="en">
<jsp:include page="includes/head.jsp"/>
<body>
<header class="develop-toolbar mdc-toolbar mdc-toolbar--platform">
    <nav>
        <div class="nav-wrapper">
            <a href="${fn:escapeXml(pageContext.request.contextPath)}//" class="brand-logo" style="font-size: 20px;">D A S H U P</a>
        </div>
    </nav>
</header>
<div class="container">
    <div class="row">
        <div class="col m6 offset-m3 s10 offset-s1">
            <h3>
                <a href="${fn:escapeXml(pageContext.request.contextPath)}/login" style="color: var(--color-black);">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <fmt:message key="i18n.registering"/>
            </h3>
        </div>
    </div>
    <div class="row">
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text-field-register-email" name="email" type="text" class="validate">
                <label for="text-field-register-email"><fmt:message key="i18n.emailAddress"/></label>
            </div>
        </div>
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text-field-register-name" name="name" type="text" class="validate">
                <label for="text-field-register-name"><fmt:message key="i18n.userName"/></label>
            </div>
        </div>
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text-field-register-password" name="password" type="password" class="validate">
                <label for="text-field-register-password"><fmt:message key="i18n.password"/></label>
            </div>
        </div>
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text-field-register-repeat-password" name="repeatPassword" type="password" class="validate">
                <label for="text-field-register-repeat-password"><fmt:message key="i18n.repeatPassword"/></label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col m6 offset-m3 s10 offset-s1">
            <button class="btn waves-effect waves-light" type="submit" name="action" id="submit-registration">
                <fmt:message key="i18n.register"/>
                <i class="fas fa-sign-in-alt"></i>
            </button>
        </div>
    </div>
</div>
</body>
<script>
    $(document).ready(function () {

        let toastOptions = {};
        switch (getAnchor()) {
            case "emailInUse":
                toastOptions = {
                    html: "<fmt:message key="i18n.emailIsAlreadyRegistered" />",
                    classes: "error"
                };
                break;
        }
        if (getAnchor() !== null && getAnchor() !== "") {
            M.toast(toastOptions);
            clearAnchor()
        }

        $("#submit-registration").on("click", function () {
            let password = $("#text-field-register-password").val(),
                repeatPassword = $("#text-field-register-repeat-password").val(),
                email = $("#text-field-register-email").val(),
                name = $("#text-field-register-name").val()
            if (email !== "" && name !== "") {
                if (password === repeatPassword && password !== "") {
                    if (password.length >= 8) {
                        PostRequest.getInstance().make("handleRegisterUser", {
                            email: $("#text-field-register-email").val(),
                            password: password,
                            repeatPassword: repeatPassword,
                            userName: $("#text-field-register-name").val(),
                        });
                    } else {
                        M.toast({
                            html: "<fmt:message key="i18n.passwordLength" />",
                            classes: "error"
                        })
                    }
                } else if (password !== repeatPassword) {
                    M.toast({
                        html: "<fmt:message key="i18n.passwordsNotMatching"/>",
                        classes: "error"
                    });
                } else if (password === repeatPassword && password === "") {
                    M.toast({
                        html: "<fmt:message key="i18n.emptyPassword"/>",
                        classes: "error"
                    });
                }
            } else {
                M.toast({
                    html: "<fmt:message key="i18n.missingPersonalInfo"/>",
                    classes: "error"
                });
            }
        });
    });
</script>
</html>