<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<jsp:include page="includes/head.jsp"/>
<head>
    <link rel='stylesheet' href='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.css'>
    <title>dashup</title>
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
            ${content}
        </div>

    </div>
</div>

<div>
    <a id="save-changes-button" href="#" class="btn-floating btn-large waves-effect waves-light">
        <i class="fas fa-check"></i>
    </a>
    <a id="add-section-button" href="#" class="left-align btn-floating btn-large waves-effect waves-light" style="margin-bottom: 75px">
        <i class="fas fa-plus"></i>
    </a>

</div>


<!-- Dropdown Structure -->
<ul id='dropdown1' class='dropdown-content'>
    <li><a id="delete" href="#">
        <i class="fas fa-trash-alt"></i>
    </a></li>
    <li class="divider" tabindex="-1"></li>
    <li><a href="#">
        small
    </a></li>
    <li><a href="#">
        large
    </a></li>
</ul>

<script src='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.js'></script>
<script src="../libraries/draganddrop.js"></script>

<script>
    function showSaveReponseSuccessMessageToast() {
        M.toast({
            html: "<fmt:message key="i18n.successChangedLayout" />",
            classes: "success"
        });
    }

    function showSaveResponseErrorMessageToast() {
        M.toast({
            html: "Error",
            classes: "error"
        });
    }
</script>

</body>
</html>
