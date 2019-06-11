<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            .dropdown-trigger {
                float: right;
                padding-top: 5px;
            }

            .widget-content {
                padding: 5px;
            }

            #add-section-button {
                margin-bottom: 150px;
            }

            #cancel-button {
                margin-bottom: 75px;
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
            <a id="cancel-button" href="#" class="left-align btn-floating btn-large waves-effect waves-light"
               style="margin-bottom: 75px">
                <i class="fas fa-times"></i>
            </a>
            <a id="add-section-button" href="#" class="left-align btn-floating btn-large waves-effect waves-light" style="">
                <i class="fas fa-plus"></i>
            </a>

        </div>

        <ul id='dropdown1' class='dropdown-content'>
            <li><a id="delete" href="#">
                <i class="colored-text fas fa-trash-alt"></i>
            </a></li>
            <li class="divider" tabindex="-1"></li>
            <li id="open-properties-dialog">
                <span class="colored-text"><fmt:message key="i18n.properties" /></span>
            </li>
            <li class="divider" tabindex="-1"></li>
            <li class="size" id="widget-size-small"><a href="#">
                <span class="colored-text"><fmt:message key="i18n.small" /></span>
            </a></li>
            <li class="size" id="widget-size-medium"><a href="#">
                <span class="colored-text"><fmt:message key="i18n.medium" /></span>
            </a></li>
            <li class="size" id="widget-size-large"><a href="#">
                <span class="colored-text"><fmt:message key="i18n.large" /></span>
            </a></li>
        </ul>

        <template id="new-section-row">
            <div class="row">
                <div class="drag-drop-btn col s6 valign-wrapper">
                    <i class="drag-drop-btn fas fa-grip-lines col s1" style="margin:0"></i>
                    <input class="col s4" type="text" style="margin:0" value="<fmt:message key="i18n.newSection"/>">
                    <i class="section-minus fas fa-minus col s1" style="margin:0"></i>
                </div>
            </div>
        </template>

        <template id="widget-container">
            <div class="bloc col s12"></div>
        </template>

        <c:forEach items="${widgets}" var="widget">
            <div id="dialog-properties-${widget.id}" class="modal col s3 m3 l3i">
                <div class="modal-content">
                    <div class="row">
                       <h4><fmt:message key="i18n.properties" /></h4>
                    </div>
                    <div id="properties-form-${widget.id}">
                        <c:forEach items="${widget.properties}" var="propertyEntry">
                            <div class="row">
                                <div class="col s6 m6">
                                    <p>${propertyEntry.value.name}</p>
                                </div>
                                <div class="col input-field s6 m6">
                                    <input id="text-input-value" data-property-id="${propertyEntry.value.id}"
                                           data-property="${propertyEntry.value.property}" type="${propertyEntry.value.type}"
                                           class="validate" value="${propertyEntry.value.value}">
                                    <label for="text-input-value"><fmt:message key="i18n.value" /></label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="modal-footer">
                    <a class="btn-submit-properties btn modal-close waves-effect waves-light"><fmt:message key="i18n.ok" /></a>
                    <a id="btn-cancel-properties" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
                </div>
            </div>
        </c:forEach>

    </body>

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

            $("#open-properties-dialog").on("click", function (){
                let widgetId = selectedWidget.substr(1);
                let propertiesDialog = M.Modal.getInstance(document.getElementById("dialog-properties-" + widgetId));
                propertiesDialog.open();
            });

            $(".btn-submit-properties").on("click", function () {
                let widgetId = selectedWidget.substr(1),
                    properties = [];
                $("#properties-form-" + widgetId + " input").each(function () {
                    properties.push({id: $(this).attr("data-property-id"), property: $(this).attr("data-property"), value: $(this).val()})
                });
                PostRequest.getInstance().make("layoutMode/updateProperties", {
                    widgetId: widgetId,
                    properties: encodeURIComponent(JSON.stringify(properties))
                });
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

            $("#cancel-button").on("click", function () {
                window.location.href = "../";
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
                };
                widgetStructure.push(widget);
            }

            return widgetStructure;
        }

        function addNewSection(sectionId) {

            let newSectionRowTemplate = document.querySelector("#new-section-row");
            let newSectionRow = document.importNode(newSectionRowTemplate.content, true);

            let widgetContainerTemplate = document.querySelector("#widget-container");
            let widgetContainer = document.importNode(widgetContainerTemplate.content, true);

            let dragAndDropContainer = document.getElementById("drag-drop-container");

            let wrapper = document.createElement("div");
            wrapper.setAttribute("class", "wrapper  col s12");
            wrapper.setAttribute("id", sectionId);

            wrapper.appendChild(newSectionRow);
            wrapper.appendChild(widgetContainer);

            dragAndDropContainer.appendChild(wrapper);

            return widgetContainer;
        }

        function addSectionToDeleteToList(sectionToDelete) {
            let sectionIdPrefix = sectionToDelete.id.substr(0, 1);

            if (sectionIdPrefix !== "n") {
                let sectionObject = {
                    sectionName: "",
                    sectionId: sectionToDelete.id,
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
</html>
