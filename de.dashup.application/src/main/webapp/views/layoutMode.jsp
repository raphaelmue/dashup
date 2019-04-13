<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<jsp:include page="includes/head.jsp"/>
<head>
    <link rel='stylesheet' href='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.css'>
    <title>dashup - <fmt:message key="i18n.layoutMode" /></title>
    <style>




        .bloc--inner {
            display: inline-block;
        }


    </style>
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<div class="container">
    <div class="row">
        <h3><fmt:message key="i18n.layoutMode"/></h3>



        <div class="drag-drop-container col s12" id="drag-drop-container">
            <%--<div class="bloc bloc--first">--%>
                <%--<div class="bloc--inner"></div>--%>
            <%--</div>--%>
            <%--<div class="bloc bloc--second">--%>
                <%--<div class="bloc--inner"></div>--%>
            <%--</div>--%>
                ${content}
        </div>









    </div>
</div>

<a href="javascript:saveChanges()" class="btn-floating btn-large waves-effect waves-light"><i class="fas fa-edit"></i></a>

<!-- Dropdown Structure -->
<ul id='dropdown1' class='dropdown-content'>
    <li><a id="delete" href="#!">
        <i class="fas fa-trash-alt"></i>
    </a></li>
    <li class="divider" tabindex="-1"></li>
    <li><a href="#!">
        small
    </a></li>
    <li><a href="#!">
        large
    </a></li>
</ul>

<script src='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.js'></script>
<script src="../libraries/draganddrop.js"></script>

</body>
</html>
