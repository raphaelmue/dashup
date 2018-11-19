<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="./includes/styles.jsp" />
        <title>dashup</title>
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
    </body>
</html>