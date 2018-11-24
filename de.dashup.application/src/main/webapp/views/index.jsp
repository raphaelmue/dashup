<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <head>
        <title>dashup</title>

        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/webComponents.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />
    </head>
    <body>
        <header>
            <div class="wrapper">
                <h1 class="heading">dashup</h1>
                <nav>
                    <a class="nav-item" href="${pageContext.request.contextPath}/marketplace"><fmt:message key="i18n.marketplace" /></a>
                    <a class="nav-item" href="${pageContext.request.contextPath}/layout"><fmt:message key="i18n.layout" /></a>
                    <a id="" href="${pageContext.request.contextPath}/profile">${name}</a>
                </nav>
                <div class="clear-float"></div>
            </div>
        </header>
        <main>
            <div class="wrapper">
                <h1><fmt:message key="i18n.welcome"/>, ${name}!</h1>
            </div>
        </main>

        <jsp:include page="./includes/bodyInclude.jsp" />
    </body>
</html>