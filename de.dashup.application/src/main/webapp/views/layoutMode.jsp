<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
    <jsp:include page="includes/head.jsp"/>
    <body>
        <jsp:include page="includes/header.jsp"/>
        <div class="container">
            <div class="row">
                <h3><fmt:message key="i18n.layoutMode" /></h3>
            </div>
        </div>
    </body>
</html>
