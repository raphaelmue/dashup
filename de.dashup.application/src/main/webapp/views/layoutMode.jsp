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
            <a href="${fn:escapeXml(pageContext.request.contextPath)}/" class="breadcrumb">dashup</a>
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

<script>

    let sectionsToDelete = [];
    let selectedWidget;
    let globalSectionCount;
    let sectionContainer;
    let widgetContainer;

    (function () {
        sectionContainer = dragula([document.querySelector(".drag-drop-container")], {
            moves: function (el, container, handle) {

                let draggedClass = handle.classList[0];

                if (draggedClass === "drag-drop-btn") {
                    return !handle.classList.contains("bloc--inner");
                }
            }
        });

        widgetContainer = dragula([].slice.apply(document.querySelectorAll(".bloc")), {
            direction: "horizontal"
        });

        document.addEventListener("DOMContentLoaded", function () {
            let elems = document.querySelectorAll(".dropdown-trigger");
            M.Dropdown.init(elems, null);
        });

        $(".dropdown-trigger").on("click", function (event) {
            let widget = event.currentTarget.parentNode.parentNode.parentNode;
            selectedWidget = widget.id;
        });

        $("#delete").on("click", function () {
            let widgetToDelete = document.getElementById(selectedWidget);
            let widgetToDeleteParent = widgetToDelete.parentNode;

            widgetToDeleteParent.removeChild(widgetToDelete);

        });

        $(".size").on("click", function (event) {
            let widgetToResize = document.getElementById(selectedWidget);

            let newSize = event["currentTarget"].id;
            if(newSize === "widget-size-small"){
                widgetToResize.setAttribute("class","bloc--inner col z-depth-1 " + "${small}");
                widgetToResize.setAttribute("size","small");
            } else if(newSize === "widget-size-medium"){
                widgetToResize.setAttribute("class","bloc--inner col z-depth-1 " + "${medium}");
                widgetToResize.setAttribute("size","medium");

            } else if(newSize === "widget-size-large"){
                widgetToResize.setAttribute("class","bloc--inner col z-depth-1 " + "${large}");
                widgetToResize.setAttribute("size","large");
            }
        });

        $("#add-section-button").on("click", function () {
            let sectionId = "n" + globalSectionCount;
            globalSectionCount++;
            let widgetContainerToAdd = addNewSection(sectionId);
            widgetContainer.containers.push(widgetContainerToAdd);
            initializeSectionDeleteClick();
        });


        $("#save-changes-button").on("click", function () {
            saveChanges();
        });

        initializeSectionDeleteClick();

        globalSectionCount = 0;

    })();

    function makeWidgetStructure(widgets) {
        let widgetsCount = widgets.length;
        let widgetStructure = [];

        for (let j = 0; j < widgetsCount; j++) {
            let widget = {
                widgetId: widgets[j].id,
                widgetSize: widgets[j]["attributes"]["size"].value,
                order: j
            };
            widgetStructure.push(widget);
        }

        return widgetStructure;
    }

    function addNewSection(sectionId) {

        let dragAndDropContainer = document.getElementById("drag-drop-container");

        let wrapper = document.createElement("div");
        wrapper.setAttribute("class", "wrapper  col s12");
        wrapper.setAttribute("id", sectionId);

        let row = document.createElement("div");
        row.setAttribute("class", "row");

        let sectionHeading = document.createElement("div");
        sectionHeading.setAttribute("class", "drag-drop-btn col s6 valign-wrapper");

        let gripLineIcon = document.createElement("i");
        gripLineIcon.setAttribute("class", "drag-drop-btn fas fa-grip-lines col s1");
        gripLineIcon.setAttribute("style", "margin:0");

        let inputField = document.createElement("input");
        inputField.setAttribute("class", "col s4");
        inputField.setAttribute("type", "text");
        inputField.setAttribute("style", "margin:0");
        inputField.setAttribute("value", "New Section");

        let minusIcon = document.createElement("i");
        minusIcon.setAttribute("class", "section-minus fas fa-minus col s1");
        minusIcon.setAttribute("style", "margin:0");

        let widgetContainer = document.createElement("div");
        widgetContainer.setAttribute("class", "bloc col s12");

        sectionHeading.appendChild(gripLineIcon);
        sectionHeading.appendChild(inputField);
        sectionHeading.appendChild(minusIcon);

        row.appendChild(sectionHeading);

        wrapper.appendChild(row);
        wrapper.appendChild(widgetContainer);

        dragAndDropContainer.appendChild(wrapper);

        return widgetContainer;
    }

    function addSectionToDeleteToList(sectionToDelete) {
        let section = sectionToDelete.childNodes[1];
        let widgets = section.childNodes;

        let layoutModeWidgets;
        layoutModeWidgets = makeWidgetStructure(widgets);
        let sectionIdPrefix = sectionToDelete.id.substr(0, 1);

        if (sectionIdPrefix !== "n") {
            let sectionObject = {
                sectionName: "",
                sectionId: sectionToDelete.id,
                layoutModeWidgets
            };

            sectionsToDelete.push(sectionObject);
        }

    }

    function makeSectionWidgetOrder() {
        let sectionWidgetOrder = [];
        let dragDropContainer = document.getElementById("drag-drop-container");
        let sections = dragDropContainer.getElementsByClassName("wrapper");
        let sectionsCount = sections.length;

        for (let i = 0; i < sectionsCount; i++) {

            let sectionName = sections[i].children[0].children[0].children[1].value;
            let sectionId = sections[i].id;
            let widgets = sections[i].children[1].children;
            let layoutModeWidgets = makeWidgetStructure(widgets);

            let sectionObject = {
                sectionName,
                sectionId,
                layoutModeWidgets,
                order: i
            };

            sectionWidgetOrder.push(sectionObject);
        }

        return sectionWidgetOrder;
    }

    function initializeSectionDeleteClick() {
        $(".section-minus").on("click", function (event) {
            let sectionToDelete = event.currentTarget.parentNode.parentNode.parentNode;

            if (sectionToDelete != null) {
                addSectionToDeleteToList(sectionToDelete);

                while (sectionToDelete.firstChild) {
                    sectionToDelete.removeChild(sectionToDelete.firstChild);
                }

                let sectionParent = sectionToDelete.parentNode;
                sectionParent.removeChild(sectionToDelete);
            }

        });
    }

    function showSaveResponseErrorMessageToast() {
        M.toast({
            html: "Error",
            classes: "error"
        });
    }

    function postChanges(data) {
        let url = "/layoutMode/handleSaveChanges";

        fetch(url, {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-Type": "application/json"
            }
        }).then((response) => {
            if (!response.ok) {
                throw new Error("error");
            }
            return response.json();

        }).then(() => {
            window.location.href = "../";
        }).catch(() => {
            showSaveResponseErrorMessageToast();
        });

    }

    function saveChanges() {
        let sectionWidgetOrder = makeSectionWidgetOrder();
        let layout = {
            sectionsToDelete,
            sectionWidgetOrder
        };

        postChanges(layout);
    }
</script>

</body>
</html>
