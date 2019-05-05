<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
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
<nav>
    <div class="nav-wrapper">
        <div class="col s12">
            <a href="${pageContext.request.contextPath}/" class="breadcrumb">dashup</a>
            <a href="#" class="breadcrumb"><fmt:message key="i18n.layoutMode" /></a>
        </div>
    </div>
</nav>
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

<ul id='dropdown1' class='dropdown-content'>
    <li><a id="delete" href="#">
        <i class="fas fa-trash-alt"></i>
    </a></li>
    <li class="divider" tabindex="-1"></li>
    <li class="size" id="widget-size-small"><a href="#">
        small
    </a></li>
    <li class="size" id="widget-size-medium"><a href="#">
        medium
    </a></li>
    <li class="size" id="widget-size-large"><a href="#">
        large
    </a></li>
</ul>

<script src='https://rawgit.com/bevacqua/dragula/master/dist/dragula.min.js'></script>
<script src="../libraries/draganddrop.js"></script>

<script>

    $(".size").on("click", function (event) {
        let widgetToResize = document.getElementById(selectedPanel);

        let newSize = event["currentTarget"].id;
        if(newSize === "widget-size-small"){
            widgetToResize.setAttribute("class","bloc--inner col z-depth-1 " + "${small}");
            widgetToResize.setAttribute("size","small");
        }
        else if(newSize === "widget-size-medium"){
            widgetToResize.setAttribute("class","bloc--inner col z-depth-1 " + "${medium}");
            widgetToResize.setAttribute("size","medium");

        }
        else if(newSize === "widget-size-large"){
            widgetToResize.setAttribute("class","bloc--inner col z-depth-1 " + "${large}");
            widgetToResize.setAttribute("size","large");
        }
    });

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
