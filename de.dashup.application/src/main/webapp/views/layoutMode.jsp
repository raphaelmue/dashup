<%--
  Created by IntelliJ IDEA.
  User: D070413
  Date: 20.11.2018
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<head>
    <title>dashup - <fmt:message key="i18n.layoutMode"/></title>


    <%--<jsp:include page="./includes/headInclude.jsp"/>--%>
    <%--<jsp:include page="./includes/styles.jsp"/>--%>
    <%--<jsp:include page="./includes/scripts.jsp"/>--%>
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">-->
    <link rel="stylesheet" href="./../styles/layoutmode.css">

    <jsp:include page="./includes/webComponents.jsp"/>
    <jsp:include page="./includes/mainLayout.jsp"/>
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">-->

    <style>
        * {
            font-family: ${font_text}, sans-serif
        }

        body {
            background: url(${background_image}) no-repeat;
            background-color: ${background_color};
            background-size: cover;
            min-height: 100vh;
        }

        .sectionHeading {
            font-size: ${heading_size}pt;
            font-family: ${font_heading};
            color: ${heading_color};
        }
    </style>

</head>
<body onload="load()">


<jsp:include page="includes/header.jsp"/>


<main>
    <div class="wrapper">
        <div>
            ${content}
        </div>
    </div>

    <div id="result"></div>
</main>

<div id="bottom-right">
    <ul>
        <button type="button" onclick="onUndo()" class="btn btn-primary">Undo</button>
    </ul>
    <ul>
        <button type="button" id="add_section_btn" onclick="addSection()" class="btn btn-primary">Add</button>
    </ul>
    <ul>
        <button type="button" onclick="onSubmit()" class="btn btn-primary">Submit</button>
    </ul>
</div>


<script src="./../libraries/dragdrop.js"></script>


</body>

</html>
