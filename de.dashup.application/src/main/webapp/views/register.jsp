<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n" scope="session"/>
<html lang="en">
<jsp:include page="includes/head.jsp"/>
<body>
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
            <h3>
                <a href="${pageContext.request.contextPath}/login" style="color: var(--color-black);">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <fmt:message key="i18n.registering"/>
            </h3>
        </div>
    </div>
    <div class="row">
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text_field_register_email" name="email" type="text" class="validate">
                <label for="text_field_register_email"><fmt:message key="i18n.emailAddress"/></label>
            </div>
        </div>
        <div class="col m3 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text_field_register_name" name="name" type="text" class="validate">
                <label for="text_field_register_name"><fmt:message key="i18n.name"/></label>
            </div>
        </div>
        <div class="col m3  s10 offset-s1">
            <div class="input-field">
                <input id="text_field_register_surname" name="surname" type="text" class="validate">
                <label for="text_field_register_surname"><fmt:message key="i18n.surname"/></label>
            </div>
        </div>
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text_field_register_password" name="password" type="password" class="validate">
                <label for="text_field_register_password"><fmt:message key="i18n.password"/></label>
            </div>
        </div>
        <div class="col m6 offset-m3 s10 offset-s1">
            <div class="input-field">
                <input id="text_field_register_repeat_password" name="repeatPassword" type="password" class="validate">
                <label for="text_field_register_repeat_password"><fmt:message key="i18n.repeatPassword"/></label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col m6 offset-m3 s10 offset-s1">
            <button class="btn waves-effect waves-light" type="submit" name="action" id="submit_registration">
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

        $("#submit_registration").on("click", function () {
            let password = $("#text_field_register_password").val(),
                repeatPassword = $("#text_field_register_repeat_password").val(),
                email = $("#text_field_register_email").val(),
                name = $("#text_field_register_name").val(),
                surname = $("#text_field_register_surname").val()
            if (email !== "" && name !== "" && surname !== "") {
                if (password === repeatPassword && password !== "") {
                    PostRequest.getInstance().make("handleRegisterUser", {
                        email: $("#text_field_register_email").val(),
                        password: password,
                        repeatPassword: repeatPassword,
                        name: $("#text_field_register_name").val(),
                        surname: $("#text_field_register_surname").val(),
                    });
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