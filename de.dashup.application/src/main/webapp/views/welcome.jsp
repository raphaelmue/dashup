<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<html>
    <head>
        <title>dashup - Welcome</title>

        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />
        <link  rel="stylesheet" type="text/css" href="./../styles/welcome.css" />
        <jsp:include page="./includes/webComponents.jsp" />
    </head>
    <body class="text-white">

        <div class="jumbotron jumbotron-fluid bg-primary text-white">
            <div class="container">
                <h1 class="display-4"><fmt:message key="i18n.welcome" /></h1>
            </div>
        </div>

        <p> Have a look at our WebComponents: </p>

        <div id="WeatherComponent">
            <dashup-weather></dashup-weather>
        </div>

        <jsp:include page="./includes/bodyInclude.jsp" />
    </body>
</html>