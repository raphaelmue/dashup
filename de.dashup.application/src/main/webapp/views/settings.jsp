<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<html>
    <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp"/>
        <nav>
            <div class="nav-wrapper">
                <div class="col s12">
                    <a href="${pageContext.request.contextPath}/" class="breadcrumb">dashup</a>
                    <a href="#" class="breadcrumb"><fmt:message key="i18n.settings" /></a>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <h3><fmt:message key="i18n.settings" /></h3>
            </div>
            <ul class="collapsible">
                <li>
                    <div class="collapsible-header"><i class="fas fa-user"></i><fmt:message key="i18n.accountManagement" /></div>
                    <div class="collapsible-body">
                        <div class="row">
                            <div class="col s4 m4">
                                <p><fmt:message key="i18n.emailAddress" /></p>
                            </div>
                            <div class="col s4 m4">
                                <p>${email}</p>
                            </div>
                            <div class="col s4 m4">
                                <p><a href="#"><fmt:message key="i18n.changeEmailAddress" /></a></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s4 m4">
                                <p><fmt:message key="i18n.password" /></p>
                            </div>
                            <div class="col s8 m8">
                                <p><a id="change-password-link" href="#"><fmt:message key="i18n.changePassword" /></a></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s4 m4">
                                <p><fmt:message key="i18n.logout" /></p>
                            </div>
                            <div class="col s8 m8">
                                <p><a id="logout-link" href="${pageContext.request.contextPath}/handleLogout"><fmt:message key="i18n.logout" /></a></p>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="fas fa-info-circle"></i><fmt:message key="i18n.personalInformation" /></div>
                    <div class="collapsible-body">
                        <form action="${pageContext.request.contextPath}/settings/changePersonalInfo" method="post">
                            <div class="row">
                                <div class="col s4 m1">
                                    <p><fmt:message key="i18n.name" /></p>
                                </div>
                                <div class="input-field col s8 m4" style="margin-top: 0">
                                    <input id="text-field-personal-info-name" name="name" type="text" class="validate"
                                            value="${name}" />
                                    <label for="text-field-personal-info-name"><fmt:message key="i18n.name" /></label>
                                </div>
                                <div class="col s4 m1 offset-m1">
                                    <p><fmt:message key="i18n.surname" /></p>
                                </div>
                                <div class="input-field col s8 m4" style="margin-top: 0">
                                    <input id="text-field-personal-info-surname" name="surname" type="text" class="validate"
                                           value="${surname}" />
                                    <label for="text-field-personal-info-surname"><fmt:message key="i18n.surname" /></label>
                                </div>
                            </div>
                            <div class="row">
                                <button class="btn waves-effect waves-light" type="submit" name="action">
                                    <fmt:message key="i18n.save" />
                                    <i class="fas fa-check"></i>
                                </button>
                            </div>
                        </form>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="fas fa-pen-fancy"></i><fmt:message key="i18n.layout" /></div>
                    <div class="collapsible-body">
                        <form action="${pageContext.request.contextPath}/settings/changeLayout" method="post">
                            <div class="row">
                                <div class="col s4 m4">
                                    <p><fmt:message key="i18n.theme" /></p>
                                </div>
                                <div class="input-field col s8 m8">
                                    <select name="theme" id="theme-dropdown">
                                        <option value="blue-sky">Blue Sky</option>
                                        <option value="black-and-white">Black and White</option>
                                        <option value="high-contrast">High Contrast</option>
                                    </select>
                                    <label><fmt:message key="i18n.theme" /></label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s4 m4">
                                    <p><fmt:message key="i18n.backgroundImage" /></p>
                                </div>
                                <div class="input-field col s8 m8" style="margin-top: 0">
                                    <input id="text-field-background-image" name="backgroundImage" type="text" class="validate"
                                           value="${backgroundImage}" />
                                    <label for="text-field-background-image"><fmt:message key="i18n.backgroundImage" /></label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s2 m2">
                                <button class="btn waves-effect waves-light" type="submit" name="action">
                                    <i class="fas fa-check"></i>
                                    <fmt:message key="i18n.save" />
                                </button>
                                </div>
                                <div class="col s2 m2 offset-m1">
                                <a id ="btn-undo-layout-changes" class="btn waves-effect waves-light">
                                    <i class="fas fa-times"></i>
                                    <fmt:message key="i18n.undo" />
                                </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="fas fa-cogs"></i><fmt:message key="i18n.other" /></div>
                    <div class="collapsible-body">
                        <div class="row">
                            <div class="col s4 m4">
                                <p><fmt:message key="i18n.language" /></p>
                            </div>
                            <div class="col s8 m8">
                                <p><a id="change-language-link" href="#dialog-change-language"><fmt:message key="i18n.changeLanguage" /></a></p>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>

        <div id="dialog-change-password" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.changePassword" /></h4>
                    <div class="input-field col s12">
                        <input id="change-password-old" type="password" class="validate">
                        <label for="change-password-old"><fmt:message key="i18n.oldPassword" /></label>
                    </div>
                    <div class="input-field col s12">
                        <input id="change-password-new" type="password" class="validate">
                        <label for="change-password-new"><fmt:message key="i18n.newPassword" /></label>
                    </div>
                    <div class="input-field col s12">
                        <input id="change-password-new-repeat" type="password" class="validate">
                        <label for="change-password-new-repeat"><fmt:message key="i18n.repeatPassword" /></label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#" id="btn-submit-change-password" class="waves-effect waves-green btn-flat"><fmt:message key="i18n.ok" /></a>
            </div>
        </div>
        <div id="dialog-change-language" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.selectLanguage" /></h4>
                    <div class="input-field col s12">
                        <select id="language-dropdown">
                            <option value="en">English</option>
                            <option value="de">German</option>
                        </select>
                        <label><fmt:message key="i18n.language" /></label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-change-language" class="modal-close waves-effect waves-green btn-flat"><fmt:message key="i18n.ok" /></a>
            </div>
        </div>
    </body>

    <script>
        $( document ).ready(function () {
            $("#nav-item-settings").parent().addClass("active");

            $("#theme-dropdown option[value=${theme}]").attr("selected", "selected");
            $('select').formSelect();

            let toastOptions = {};
            switch (getAnchor()) {
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
            }
            if (getAnchor() !== null && getAnchor() !== "") {
                M.toast(toastOptions);
                clearAnchor()
            }

            let changeLanguageDialog = M.Modal.getInstance(document.getElementById("dialog-change-language"));
            $('#change-language-link').on("click", function () {
                changeLanguageDialog.open();
            });

            let changePasswordDialog = M.Modal.getInstance(document.getElementById("dialog-change-password"));
            $("#change-password-link").on("click", function () {
                changePasswordDialog.open();
            });

            $('#btn-undo-layout-changes').on("click", function () {
                if (window.location.href.slice(-1) === '#') {
                    window.location.href += 'undoComplete';
                }else {
                    window.location.href += '#undoComplete';
                }
                window.location.reload(false);
            });
            $('#btn-submit-change-language').on("click", function () {
                PostRequest.getInstance().make("settings/changeLanguage", {
                    lang: $("#language-dropdown").val()
                });
            });
            $("#btn-submit-change-password").on("click", function () {
                let oldPassword = $("#change-password-old").val(),
                    newPassword = $("#change-password-new").val(),
                    newRepeatPassword = $("#change-password-new-repeat").val()

                if (newPassword === newRepeatPassword) {
                    if (oldPassword !== newPassword) {
                        changePasswordDialog.close();
                        PostRequest.getInstance().make("settings/changePassword", {
                            oldPassword: oldPassword,
                            newPassword: newPassword,
                            newRepeatPassword: newRepeatPassword
                        });
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
