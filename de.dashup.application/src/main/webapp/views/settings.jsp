<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<html>
<jsp:include page="includes/head.jsp"/>
<body>
<jsp:include page="includes/header.jsp"/>
<nav>
    <div class="nav-wrapper">
        <div class="col s12">
            <a href="${pageContext.request.contextPath}/" class="breadcrumb">dashup</a>
            <a href="#" class="breadcrumb"><fmt:message key="i18n.settings"/></a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <h3><fmt:message key="i18n.settings"/></h3>
    </div>
    <ul class="collapsible">
        <li>
            <div class="collapsible-header"><i class="fas fa-user"></i><fmt:message key="i18n.accountManagement"/></div>
            <div class="collapsible-body">
                <div id="row-display-email" class="row">
                    <div class="col s4 m4">
                        <p><fmt:message key="i18n.emailAddress"/></p>
                    </div>
                    <div class="col s4 m4">
                        <p>${fn:escapeXml(email)}</p>
                    </div>
                    <div class="col s4 m4">
                        <p><a id="link-open-change-email" href="#"><fmt:message key="i18n.changeEmailAddress"/></a></p>
                    </div>
                </div>
                <div id="row-change-email" class="row" style="display: none;">
                    <div class="col s4 m4">
                        <p><fmt:message key="i18n.emailAddress"/></p>
                    </div>
                    <div class="input-field col s4 m4" style="margin: 0">
                        <input id="text-field-email" name="name" type="text" class="validate"
                               value="${fn:escapeXml(email)}"/>
                        <label for="text-field-email"><fmt:message key="i18n.emailAddress"/></label>
                    </div>
                    <div class="col s4 m4">
                        <a id="btn-cancel-change-email" class="waves-effect btn-flat">
                            <i class="fas fa-times" style="color: var(--color-error)"></i>
                        </a>
                        <a id="btn-change-email" class="waves-effect btn-flat">
                            <i class="fas fa-check" style="color: var(--color-success)"></i>
                        </a>
                    </div>
                </div>
                <div class="row">
                    <div class="col s4 m4">
                        <p><fmt:message key="i18n.password"/></p>
                    </div>
                    <div class="col s8 m8">
                        <p><a id="change-password-link" href="#"><fmt:message key="i18n.changePassword"/></a></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col s4 m4">
                        <p><fmt:message key="i18n.logout"/></p>
                    </div>
                    <div class="col s8 m8">
                        <p><a id="logout-link"
                              href="${fn:escapeXml(pageContext.request.contextPath)}/handleLogout"><fmt:message
                                key="i18n.logout"/></a></p>
                    </div>
                </div>
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="fas fa-info-circle"></i><fmt:message
                    key="i18n.personalInformation"/></div>
            <div class="collapsible-body">
                <form action="${pageContext.request.contextPath}/settings/changePersonalInfo" method="post">
                    <div class="row">
                        <div class="input-field col s12 m6" style="margin-top: 0">
                            <input id="text-field-personal-info-name" name="name" type="text" class="validate"
                                   value="${fn:escapeXml(name)}"/>
                            <label for="text-field-personal-info-name"><fmt:message key="i18n.name"/></label>
                        </div>
                        <div class="input-field col s12 m6" style="margin-top: 0">
                            <input id="text-field-personal-info-surname" name="surname" type="text" class="validate"
                                   value="${fn:escapeXml(surname)}"/>
                            <label for="text-field-personal-info-surname"><fmt:message key="i18n.surname"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <button class="btn waves-effect waves-light" type="submit" name="action">
                            <fmt:message key="i18n.save"/>
                            <i class="fas fa-check"></i>
                        </button>
                    </div>
                </form>
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="fas fa-pen-fancy"></i><fmt:message key="i18n.layout"/></div>
            <div class="collapsible-body">
                <form action="${fn:escapeXml(pageContext.request.contextPath)}/settings/changeLayout" method="post">
                    <div class="row">
                        <div class="col s4 m4">
                            <p><fmt:message key="i18n.theme"/></p>
                        </div>
                        <div class="input-field col s8 m8">
                            <select name="theme" id="theme-dropdown">
                                <option value="blue-sky">Blue Sky</option>
                                <option value="green-nature">Green Nature</option>
                                <option value="red-love">Red Love</option>
                                <option value="white-diamond">White Diamond</option>
                                <option value="black-night">Black Night</option>
                            </select>
                            <label><fmt:message key="i18n.theme"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s4 m4">
                            <p><fmt:message key="i18n.backgroundImage"/></p>
                        </div>
                        <div class="input-field col s8 m8" style="margin-top: 0">
                            <input id="text-field-background-image" name="backgroundImage" type="text" class="validate"
                                   value="${fn:escapeXml(backgroundImage)}"/>
                            <label for="text-field-background-image"><fmt:message key="i18n.backgroundImage"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s2 m2">
                            <button class="btn waves-effect waves-light" type="submit" name="action">
                                <i class="fas fa-check"></i>
                                <fmt:message key="i18n.save"/>
                            </button>
                        </div>
                        <div class="col s2 m2 offset-m1">
                            <a id="btn-undo-layout-changes" class="btn waves-effect waves-light">
                                <i class="fas fa-times"></i>
                                <fmt:message key="i18n.undo"/>
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="fas fa-cogs"></i><fmt:message key="i18n.other"/></div>
            <div class="collapsible-body">
                <div class="row">
                    <div class="col s4 m4">
                        <p><fmt:message key="i18n.language"/></p>
                    </div>
                    <div class="input-field col s8 m8" style="margin: 0">
                        <select id="language-dropdown">
                            <option value="en">English</option>
                            <option value="de">Deutsch</option>
                        </select>
                        <label><fmt:message key="i18n.language"/></label>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</div>

<div id="dialog-change-password" class="modal">
    <div class="modal-content">
        <div class="row">
            <h4><fmt:message key="i18n.changePassword"/></h4>
            <div class="input-field col s12">
                <input id="change-password-old" type="password" class="validate">
                <label for="change-password-old"><fmt:message key="i18n.oldPassword"/></label>
            </div>
            <div class="input-field col s12">
                <input id="change-password-new" type="password" class="validate">
                <label for="change-password-new"><fmt:message key="i18n.newPassword"/></label>
            </div>
            <div class="input-field col s12">
                <input id="change-password-new-repeat" type="password" class="validate">
                <label for="change-password-new-repeat"><fmt:message key="i18n.repeatPassword"/></label>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" id="btn-submit-change-password" class="waves-effect waves-green btn-flat"><fmt:message
                key="i18n.ok"/></a>
    </div>
</div>
</body>

<script>
    $(document).ready(function () {
        $("#nav-item-settings").parent().addClass("active");

        $("#theme-dropdown option[value=${fn:escapeXml(theme)}]").attr("selected", "selected");
        $("#language-dropdown option[value=${fn:escapeXml(language)}]").attr("selected", "selected");
        $('select').formSelect();

        let toastOptions = {};
        switch (getAnchor()) {
            case "changedEmail":
                toastOptions = {
                    html: "<fmt:message key="i18n.successChangedEmail" />",
                    classes: "success"
                };
                break;
            case "invalidEmail":
                toastOptions = {
                    html: "<fmt:message key="i18n.errorInvalidEmail" />",
                    classes: "error"
                };
                break;
            case "changedPassword":
                toastOptions = {
                    html: "<fmt:message key="i18n.successChangedPassword" />",
                    classes: "success"
                };
                break;
            case "oldPasswordWrong":
                toastOptions = {
                    html: "<fmt:message key="i18n.oldPasswordIsWrong" />",
                    classes: "error"
                };
                break;
            case "changedLayout":
                toastOptions = {
                    html: "<fmt:message key="i18n.successChangedLayout" />",
                    classes: "success"
                };
                break;
            case "changedLanguage":
                toastOptions = {
                    html: "<fmt:message key="i18n.successChangedLanguage" />",
                    classes: "success"
                };
                break;
            case "changedPersonalInfo":
                toastOptions = {
                    html: "<fmt:message key="i18n.dataSuccessfullySaved" />",
                    classes: "success"
                };
                break;
            case "undoComplete":
                toastOptions = {
                    html: "<fmt:message key="i18n.undoComplete" />",
                    classes: "success"
                };
                break;
            case "generalError":
                toastOptions = {
                    html: "<fmt:message key="i18n.generalError" />",
                    classes: "error"
                };
                break;
        }
        if (getAnchor() !== null && getAnchor() !== "") {
            M.toast(toastOptions);
            clearAnchor()
        }

        $("#link-open-change-email").on("click", function () {
            $("#row-change-email").css("display", "block");
            $("#row-display-email").css("display", "none");
        });

        $("#btn-cancel-change-email").on("click", function () {
            $("#row-change-email").css("display", "none");
            $("#row-display-email").css("display", "block");
        });

        $("#btn-change-email").on("click", function () {
            let email = $("#text-field-email").val();
            if (email !== "") {
                PostRequest.getInstance().make("settings/changeEmail", {
                    email: email
                });
            } else {
                M.toast({
                    html: "<fmt:message key="i18n.missingPersonalInfo" />",
                    classes: "error"
                });
            }
        });

        $("#language-dropdown").change(function () {
            PostRequest.getInstance().make("settings/changeLanguage", {
                lang: $("#language-dropdown").val()
            });
        });

        let changePasswordDialog = M.Modal.getInstance(document.getElementById("dialog-change-password"));
        $("#change-password-link").on("click", function () {
            changePasswordDialog.open();
        });

        $('#btn-undo-layout-changes').on("click", function () {
            if (window.location.href.slice(-1) === '#') {
                window.location.href += 'undoComplete';
            } else {
                window.location.href += '#undoComplete';
            }
            window.location.reload(false);
        });

        $("#btn-submit-change-password").on("click", function () {
            let oldPassword = $("#change-password-old").val(),
                newPassword = $("#change-password-new").val(),
                newRepeatPassword = $("#change-password-new-repeat").val()

            if (newPassword === newRepeatPassword) {
                if (oldPassword !== newPassword) {
                    if (newPassword.length >= 8) {
                        changePasswordDialog.close();
                        PostRequest.getInstance().make("settings/changePassword", {
                            oldPassword: oldPassword,
                            newPassword: newPassword,
                            newRepeatPassword: newRepeatPassword
                        });
                    } else {
                        M.toast({
                            html: "<fmt:message key="i18n.passwordLength" />",
                            classes: "error"
                        })
                    }
                } else {
                    M.toast({
                        html: "<fmt:message key="i18n.oldAndNewPasswordMustDiffer"/>",
                        classes: "error"
                    });
                }
            } else {
                M.toast({
                    html: "<fmt:message key="i18n.passwordsNotMatching"/>",
                    classes: "error"
                });
            }
        });
    });
</script>
</html>
