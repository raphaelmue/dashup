<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<html>
    <head>
        <title>dashup - Welcome</title>

        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/webComponents.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />
        <link  rel="stylesheet" type="text/css" href="./../styles/welcome.css" />

    </head>
    <body class="text-white">

        <div class="jumbotron jumbotron-fluid bg-primary text-white">
            <div class="container">
                <h1 class="display-4"><fmt:message key="i18n.welcome" /></h1>
            </div>
        </div>

        <div id="WeatherComponent" class="container col-lg-4 col-lg-offset-4">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/entry/login" role="button">Login</a>
            <br />
            <br />

            <dashup-weather city="Walldorf"></dashup-weather>
        </div>

        <br  />

        <div id="CustomWeatherComponent" class="container col-lg-8 col-lg-offset-2">
            <div style="border-style: groove; padding: 15px">
                <h1>
                    <img id="icon" src=""/>
                    Weather in <dashup-input name="weatherInput" action="data" api="http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c"
                                             param="q" consumers="temperature description"></dashup-input>
                </h1>
                <p>
                    <span><strong>Temperature:   </strong><dashup-display name="temperature"></dashup-display></span>
                    <span style="float: right"><strong>Forecast:   </strong><span><dashup-display name="description"></dashup-display></span></span>
                </p>
            </div>

            <br  />

        </div>

        <jsp:include page="./includes/bodyInclude.jsp" />
    </body>
</html>