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
            <div class="container">
                <div class="row">
                    <div class="col-sm">
                        <h4><fmt:message key="i18n.basicInformation"/></h4>
                        <table class="table">
                            <tbody>
                                <tr>
                                    <td><fmt:message key="i18n.name"/></td>
                                    <td>${fullName}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="i18n.emailAddress"/></td>
                                    <td>${email}</td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="i18n.password"/></td>
                                    <td><a href=""><fmt:message key="i18n.changePassword"/></a></td>
                                </tr>
                            </tbody>
                        </table>

                        <h4><fmt:message key="i18n.additionalInformation"/></h4>
                        <table class="table">
                            <tbody>
                            <tr>
                                <td><fmt:message key="i18n.gender"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="i18n.birthDate"/></td>
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
                                    <td><fmt:message key="i18n.language" /></td>
                                    <td><a href=""><fmt:message key="i18n.changeLanguage" /></a></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="i18n.layout" /></td>
                                    <td><a href="${pageContext.request.contextPath}../layout"><fmt:message key="i18n.changeLayout" /></a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
