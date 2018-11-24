<%--
  Created by IntelliJ IDEA.
  User: D070413
  Date: 20.11.2018
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<html>




<head>
    <title>dashup - Welcome</title>

    <jsp:include page="./includes/headInclude.jsp" />
    <jsp:include page="./includes/styles.jsp" />
    <jsp:include page="./includes/scripts.jsp" />
    <link  rel="stylesheet" type="text/css" href="./../styles/welcome.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="./../styles/layoutmode.css">
    <jsp:include page="./includes/webComponents.jsp" />

</head>
<body class="text-white" onload="load()">

<div class="jumbotron jumbotron-fluid bg-primary text-white">
    <div class="container">
        <h1 class="display-4"><fmt:message key="i18n.layout" /></h1>
    </div>
</div>

<div class="container">
<div id="drag_container_section">

    <div class="section">

        <div>
            <span class="handle">#</span>
            <span><h3>Section 1</h3></span>
        </div>


        <hr/>
        <div class="drag_container" id="drag_container1">
            <div id="p1" class="panel">Panel 1</div>
            <div class="panel">Panel 2</div>
            <div class="panel">Panel 3</div>
            <div class="panel">Panel 4</div>
            <div id="WeatherComponent" class="container col-lg-4 col-lg-offset-4">
                <dashup-weather city="Walldorf"></dashup-weather>
            </div>

        </div>
    </div>
    <div class="section">

        <div>
            <span class="handle">#</span>
            <span><h3>Section 1</h3></span>
        </div>


        <hr/>
        <div class="drag_container" id="drag_container2">
            <div class="panel">Panel 1</div>
            <div class="panel">Panel 2</div>
            <div class="panel">Panel 3</div>
            <div class="panel">Panel 4</div>


        </div>
    </div>
</div>
</div>


<jsp:include page="./includes/bodyInclude.jsp" />
<script  src="./../scripts/dragdrop.js"></script>


</body>

</body>
</html>
