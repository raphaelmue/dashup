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
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">-->
    <link rel="stylesheet" href="./../styles/layoutmode.css">
    <jsp:include page="./includes/webComponents.jsp" />
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">-->

</head>
<body class="text-white" onload="load()">

<div class="jumbotron jumbotron-fluid bg-primary text-white">
    <div class="container">
        <h1 class="display-4"><fmt:message key="i18n.layout" /></h1>
    </div>
</div>

<div class="container">
<div id="drag_container_section">

    <div class="dashup_section" id="s1">

        <div>
            <span class="handle">#</span>
            <span><h3>Section 1</h3></span>
        </div>


        <hr/>
        <div class="drag_container" id="drag_container1">
            <div id="p1" class="dashup_panel">Panel 1</div>
            <div id="p2" class="dashup_panel">Panel 2</div>
            <div id="p3" class="dashup_panel">Panel 3</div>
            <div id="p4" class="dashup_panel">Panel 4</div>
            </div>

        </div>
    <div class="dashup_section" id="s2">

        <div>
            <span class="handle">#</span>
            <span><h3>Section 2</h3></span>
        </div>


        <hr/>
        <div class="drag_container" id="drag_container2">
            <div id="p5" class="dashup_panel">Panel 1</div>
            <div id="p6" class="dashup_panel">Panel 2</div>

        </div>
    </div>
    </div>



</div>
</div>

<div id="bottomRight">
    <ul><button type="button" class="btn btn-circle btn-lg"><i class="glyphicon glyphicon-remove"></i></button></ul>
    <ul><button type="button" class="btn btn-circle btn-lg"><i class="glyphicon glyphicon-repeat"></i></button></ul>
    <ul><button type="button" onclick="onSubmit()" class="btn btn-circle btn-lg"><i class="glyphicon glyphicon-ok"></i></button></ul>
</div>


<jsp:include page="./includes/bodyInclude.jsp" />
<script  src="./../scripts/dragdrop.js"></script>


</body>

</body>
</html>
