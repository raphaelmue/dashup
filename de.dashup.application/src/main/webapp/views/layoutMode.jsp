<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<jsp:include page="includes/head.jsp"/>
<head>
    <link rel='stylesheet' href='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.css'>
    <title>dashup - <fmt:message key="i18n.layoutMode" /></title>
    <style>


        .bloc {
            width: 400px;
            height: 200px;
            margin-bottom: 20px;
            padding: 20px;
            font-size: 0;
        }

        .bloc--first {
            background: red;
        }

        .bloc--second {
            background: green;
        }

        .bloc--inner {
            display: inline-block;
            width: calc((100% - 20px) / 2);
            height: 100%;
            background: blue;
            margin-right: 20px;
        }
        .bloc--inner:last-of-type {
            margin: 0;
        }

    </style>
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<div class="container">
    <div class="row">
        <h3><fmt:message key="i18n.layoutMode"/></h3>



        <div class="drag-drop-container">
            <div class="bloc bloc--first">
                <div class="bloc--inner"></div>
            </div>
            <div class="bloc bloc--second">
                <div class="bloc--inner"></div>
            </div>
        </div>


        <script src='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.js'></script>
        <script src="../libraries/draganddrop.js"></script>





    </div>
</div>
</body>
</html>
