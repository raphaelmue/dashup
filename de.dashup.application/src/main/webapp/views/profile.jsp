<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<head>
    <jsp:include page="./includes/headInclude.jsp" />
    <jsp:include page="./includes/styles.jsp" />
    <jsp:include page="./includes/scripts.jsp" />

    <title>dashup - <fmt:message key="i18n.profile"/></title>
</head>
<body>
    <jsp:include page="includes/mainHeader.jsp"/>
    <main>
        <div class="wrapper">
            <h1><fmt:message key="i18n.profile"/></h1>
            <div class="success-box">${successMessage}</div>
            <div class="container">
                <div class="row">
                    <div class="col-sm">
                        <h4><fmt:message key="i18n.basicInformation"/></h4>
                        <table class="table">
                            <tbody>
                                <tr>
                                    <th scope="row"><fmt:message key="i18n.name"/></th>
                                    <td>${fullName}</td>
                                </tr>
                                <tr>
                                    <th scope="row"><fmt:message key="i18n.emailAddress"/></th>
                                    <td>${email}</td>
                                </tr>
                                <tr>
                                    <th scope="row"><fmt:message key="i18n.password"/></th>
                                    <td><a href="#"><fmt:message key="i18n.changePassword"/></a></td>
                                </tr>
                            </tbody>
                        </table>

                        <h4><fmt:message key="i18n.additionalInformation"/></h4>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.gender"/></th>
                                <td></td>
                            </tr>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.birthDate"/></th>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-sm">
                        <h4><fmt:message key="i18n.systemSettings" /></h4>
                        <table class="table">
                            <tbody>
                                <tr>
                                    <th scope="row"><fmt:message key="i18n.language" /></th>
                                    <td>${language}</td>
                                    <td><a id="change-language-link" href="#"><fmt:message key="i18n.changeLanguage" /></a></td>
                                </tr>
                                <tr>
                                    <th scope="row"><fmt:message key="i18n.layout" /></th>
                                    <td></td>
                                    <td><a href="${pageContext.request.contextPath}../layout"><fmt:message key="i18n.changeLayout" /></a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script>
        $(document).ready(function() {
            PostRequest.getInstance().setHost(true);

            $("#change-language-link").on("click", function () {
                $.confirm({
                    title: "<fmt:message key="i18n.changeLanguage"/>",
                    content:
                        "<select id=\"language-dropdown\" name=\"language-dropdown\" class=\"custom-select\">" +
                        "<option value=\"en\">English</option> " +
                        "<option value=\"de\">German</option> " +
                        "</select>",
                    closeIcon: true,
                    buttons: {
                        ok: {
                            text: "<fmt:message key="i18n.ok"/>",
                            btnClass: 'btn-blue',
                            action: function() {
                                PostRequest.getInstance().make("profile/changeLanguage", {
                                    lang: $("#language-dropdown").val()
                                });
                            }
                        }
                    }
                })
            })
        })
    </script>
</body>
</html>
