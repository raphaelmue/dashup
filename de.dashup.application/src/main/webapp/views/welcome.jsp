<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<html>
    <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp" />


    </body>
</html>