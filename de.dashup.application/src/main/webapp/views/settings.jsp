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
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="fas fa-info-circle"></i><fmt:message key="i18n.personalInformation" /></div>
                    <div class="collapsible-body"><span>Lorem ipsum dolor sit amet.</span></div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="fas fa-pen-fancy"></i><fmt:message key="i18n.layout" /></div>
                    <div class="collapsible-body"><span>Lorem ipsum dolor sit amet.</span></div>
                </li>
                <li>
                    <div class="collapsible-header"><i class="fas fa-cogs"></i><fmt:message key="i18n.other" /></div>
                    <div class="collapsible-body">
                        <div class="row">
                            <div class="col s4 m4">
                                <p><fmt:message key="i18n.language" /></p>
                            </div>
                            <div class="col s8 m8">
                                <p><a id="change-language-link" href="#"><fmt:message key="i18n.changeLanguage" /></a></p>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </body>

    <script>
        $( document ).ready(function () {
            $("#nav-item-settings").parent().addClass("active");

            $("#change-password-link").on("click", function () {
                $.confirm({
                    title: "<fmt:message key="i18n.changePassword"/>",
                    content:
                        "<div class=\"form-group\">" +
                        "<label class=\"text\" for=\"password\"><fmt:message key="i18n.oldPassword" /></label>" +
                        "<input type=\"password\" class=\"form-control\" id=\"old-password\" " +
                        "placeholder=\"<fmt:message key="i18n.oldPassword" />\">" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<label class=\"text\" for=\"password\"><fmt:message key="i18n.newPassword" /></label>" +
                        "<input type=\"password\" class=\"form-control\" id=\"new-password\" " +
                        "placeholder=\"<fmt:message key="i18n.newPassword" />\" />"+
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<input type=\"password\" class=\"form-control\" id=\"new-repeat-password\" " +
                        "placeholder=\"<fmt:message key="i18n.repeatPassword" />\">" +
                        "</div>",
                    closeIcon: true,
                    buttons: {
                        ok: {
                            text: "<fmt:message key="i18n.ok"/>",
                            btnClass: 'btn-blue',
                            action: function() {
                                if ($("#new-password").val() === $("#new-repeat-password").val()) {
                                    if ($("#old-password").val() != $("#new-password").val()) {
                                        PostRequest.getInstance().make("settings/changePassword", {
                                            oldPassword: $("#old-password").val(),
                                            newPassword: $("#new-password").val(),
                                            newRepeatPassword: $("#new-repeat-password").val()
                                        });
                                    } else {
                                        $.alert("<fmt:message key="i18n.oldAndNewPasswordMustDiffer"/>");
                                    }
                                } else {
                                    $.alert("<fmt:message key="i18n.passwordsNotMatching"/>");
                                    return false;
                                }
                            }
                        }
                    }
                });
            });

            $("#change-language-link").on("click", function () {
                $.confirm({
                    title: "<fmt:message key="i18n.changeLanguage"/>",
                    content: "<div class=\"container\"><div class=\"row\"><div id=\language-dropdown\" class=\"input-field col s12\"><select>" +
                        "<option value=\"en\">English</option> " +
                        "<option value=\"de\">German</option> " +
                        "</select><label><fmt:message key="i18n.language" /></label></div></div></div>",
                    closeIcon: true,
                    buttons: {
                        ok: {
                            text: "<fmt:message key="i18n.ok"/>",
                            btnClass: 'btn-blue',
                            action: function() {
                                PostRequest.getInstance().make("settings/changeLanguage", {
                                    lang: $("#language-dropdown").val()
                                });
                            }
                        }
                    }
                });
            });
        });
    </script>
</html>
