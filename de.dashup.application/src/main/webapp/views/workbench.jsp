<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp"/>
        <nav>
            <div class="nav-wrapper">
                <div class="col s12">
                    <a href="${fn:escapeXml(pageContext.request.contextPath)}/" class="breadcrumb">dashup</a>
                    <a class="breadcrumb" href="${fn:escapeXml(pageContext.request.contextPath)}/workbench/">
                        <fmt:message key="i18n.workbench" />
                    </a>
                    <c:if test="${fn:escapeXml(current.id) > 0}">
                        <a class="breadcrumb" href="${fn:escapeXml(pageContext.request.contextPath)}/workbench/draft/${fn:escapeXml(current.id)}">
                            ${fn:escapeXml(current.name)}
                        </a>
                    </c:if>
                </div>
            </div>
        </nav>
        <aside>
            <div>
                <h4><fmt:message key="i18n.workbench" /></h4>
                <hr />
                <div class="row">
                    <h6 class="sub-title"><fmt:message key="i18n.drafts" /></h6>
                    <ul>
                        <c:forEach items="${drafts}" var="draft">
                            <li>
                                <a href="${fn:escapeXml(pageContext.request.contextPath)}/workbench/draft/${fn:escapeXml(draft.id)}" class="waves-effect <c:if test="${draft.id == current.id}">active</c:if>">${fn:escapeXml(draft.name)}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <hr />
                <div class="row">
                    <h6 class="sub-title"><fmt:message key="i18n.publishedWidgets" /></h6>
                    <ul>
                        <c:forEach items="${publishedWidgets}" var="widget">
                            <li>
                                <a href="${fn:escapeXml(pageContext.request.contextPath)}/workbench/published/${fn:escapeXml(widget.id)}" class="waves-effect <c:if test="${widget.id == current.id}">active</c:if>">${fn:escapeXml(widget.name)}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </aside>
        <main class="container">
            <c:choose>
                <c:when test="${fn:escapeXml(current.id) > 0}">
                    <div class="container" style="width: 90%; margin-top: 0">
                        <div class="row">
                            <button id="btn-delete-draft" class="btn-flat waves-effect">
                                <i class="fas fa-trash-alt"></i>
                                <fmt:message key="i18n.delete"/>
                            </button>
                            <c:if test="${current.isVisible == false}">
                                <button id="btn-publish-widget" class="btn-flat waves-effect">
                                    <i class="fas fa-truck"></i>
                                    <fmt:message key="i18n.publish"/>
                                </button>
                            </c:if>
                            <button id="btn-add-widget" class="btn-flat waves-effect">
                                <i class="fas fa-plus"></i>
                                <fmt:message key="i18n.add"/>
                            </button>
                        </div>
                        <div class="row z-depth-1" style="margin: 0">
                            <div class="col s12">
                                <ul class="tabs" style="margin: 0">
                                    <li class="tab"><a class="active" href="#code"><fmt:message key="i18n.code" /></a></li>
                                    <li class="tab"><a href="#properties"><fmt:message key="i18n.properties" /></a></li>
                                    <li class="tab"><a href="#basicInformation"><fmt:message key="i18n.basicInformation" /></a></li>
                                </ul>
                            </div>
                            <div id="code" class="col s12">
                                <div class="row">
                                    <div class="col s12 m12">
                                        <div class="row">
                                            <div class="col s12 m6">
                                                <h4 style="margin-bottom: 20px;"><fmt:message key="i18n.preview" /></h4>
                                            </div>
                                            <div class="col s12 m6 input-field right-align">
                                                <select name="size" id="size-dropdown">
                                                    <option value="small" selected="selected"><fmt:message key="i18n.small" /></option>
                                                    <option value="medium"><fmt:message key="i18n.medium" /></option>
                                                    <option value="large"><fmt:message key="i18n.large" /></option>
                                                </select>
                                                <label><fmt:message key="i18n.size" /></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col s6 m6 l6 xl6" style="float: none; margin: 0 auto;">
                                                <div class="widget card">
                                                    <div class="card-content" id="pre-view-container">${current.codeSmall}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12">
                                        <h4 style="margin-bottom: 20px;"><fmt:message key="i18n.code" /></h4>
                                        <div class="row">
                                            <div class="col s12 m12" id="code-container">
                                                <div class="input-field">
                                                    <textarea id="textarea-code-small"
                                                              class="materialize-textarea code-textarea">${fn:escapeXml(current.codeSmall)}</textarea>
                                                    <label for="textarea-code-small"><fmt:message key="i18n.code" /></label>
                                                </div>
                                                <div class="input-field" style="display: none;">
                                                    <textarea id="textarea-code-medium"
                                                              class="materialize-textarea code-textarea">${fn:escapeXml(current.codeMedium)}</textarea>
                                                    <label for="textarea-code-medium"><fmt:message key="i18n.code" /></label>
                                                </div>
                                                <div class="input-field" style="display: none;">
                                                    <textarea id="textarea-code-large"
                                                              class="materialize-textarea code-textarea">${fn:escapeXml(current.codeLarge)}</textarea>
                                                    <label for="textarea-code-large"><fmt:message key="i18n.code" /></label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col s12 m12">
                                        <button id="btn-save-code" class="btn waves-effect waves-light">
                                            <i class="fas fa-check"></i>
                                            <fmt:message key="i18n.save"/>
                                        </button>
                                        <button id="btn-undo-code" class="btn-flat undo-button waves-effect">
                                            <i class="fas fa-times"></i>
                                            <fmt:message key="i18n.undo"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div id="properties" class="col s12">
                                <div class="row">
                                    <h3 class="col"><fmt:message key="i18n.basicInformation"/></h3>
                                </div>
                                <div class="properties-container">
                                    <c:forEach items="${current.properties}" var="propertyEntry">
                                        <div class="row property-row" data-property-id="${propertyEntry.value.id}">
                                            <div class="col input-field s3 m3">
                                                <input id="text-field-property-name" type="text" class="validate"
                                                       value="${propertyEntry.value.name}"/>
                                                <label for="text-field-property-name"><fmt:message key="i18n.propertyName"/></label>
                                            </div>
                                            <div class="col input-field s3 m3">
                                                <input id="text-field-property" type="text" class="validate"
                                                       value="${propertyEntry.value.property}"/>
                                                <label for="text-field-property"><fmt:message key="i18n.property"/></label>
                                            </div>
                                            <div class="col input-field s2 m2">
                                                <select id="text-field-property-type">
                                                    <c:forEach items="${propertyTypes}" var="type" >
                                                        <option value="${type.name}" <c:if test="${type.name == propertyEntry.value.type}">selected</c:if>><fmt:message key="i18n.${type.name}"/></option>
                                                    </c:forEach>
                                                </select>
                                                <label for="text-field-property-type"><fmt:message key="i18n.propertyType"/></label>
                                            </div>
                                            <div class="col input-field s3 m3">
                                                <input id="text-field-property-default-value" type="text" class="validate"
                                                       value="${propertyEntry.value.defaultValue}" />
                                                <label for="text-field-property-default-value"><fmt:message key="i18n.defaultValue"/></label>
                                            </div>
                                            <div class="col input-field s1 m1">
                                                <a class="btn-delete-property waves-effect btn-flat">
                                                    <i class="fas fa-times" style="color: var(--color-error)"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="row">
                                        <div class="col s12 m12">
                                            <button id="btn-update-properties" class="btn waves-light">
                                                <i class="fas fa-check"></i>
                                                <fmt:message key="i18n.save" />
                                            </button>
                                            <button id="btn-add-property" class="btn-flat waves-light">
                                                <i class="fas fa-plus"></i>
                                                <fmt:message key="i18n.add" />
                                            </button>
                                            <a id="btn-undo-properties" class="btn-flat undo-button waves-effect">
                                                <i class="fas fa-times"></i>
                                                <fmt:message key="i18n.undo"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="basicInformation" class="col s12">
                                <div class="row">
                                    <h3 class="col"><fmt:message key="i18n.basicInformation"/></h3>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 m12">
                                        <input id="text-field-draft-name" type="text" class="validate" name="draftName"
                                               value="${fn:escapeXml(current.name)}"/>
                                        <label for="text-field-draft-name"><fmt:message
                                                key="i18n.draftName"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 m12">
                                        <textarea id="textarea-short-description" name="shortDescription"
                                                      class="materialize-textarea" data-length="256">${fn:escapeXml(current.shortDescription)}</textarea>
                                        <label for="textarea-short-description"><fmt:message
                                                key="i18n.shortDescription"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 m12">
                                        <textarea id="textarea-description" name="description"
                                                      class="materialize-textarea">${fn:escapeXml(current.description)}</textarea>
                                        <label for="textarea-description"><fmt:message
                                                key="i18n.description"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 m6">
                                        <select name="size" id="category-dropdown">
                                            <option value="" disabled selected><fmt:message key="i18n.selectCategory" /></option>
                                            <c:forEach items="${categories}" var="category">
                                                <option value="${category.name}" <c:if test="${current.category eq category.name}">selected</c:if>><fmt:message key="i18n.${category.name}" /></option>
                                            </c:forEach>
                                        </select>
                                        <label><fmt:message key="i18n.category" /></label>
                                    </div>
                                    <div class="col s12 m6">
                                        <div id="chips-tags" class="chips chips-autocomplete"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <button id="btn-save-draft-information" class="btn waves-effect waves-light"
                                                type="submit">
                                            <i class="fas fa-check"></i>
                                            <fmt:message key="i18n.save"/>
                                        </button>
                                        <a id="btn-undo-draft-information" class="btn-flat undo-button waves-effect">
                                            <i class="fas fa-times"></i>
                                            <fmt:message key="i18n.undo"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; margin: 20% auto 0%;">
                        <h5 style="text-align: center;">
                            <i style="font-size: 50px; margin-bottom: 20px;" class="fas fa-drafting-compass"></i>
                            <br />
                            <fmt:message key="i18n.selectDraft" />
                        </h5>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
        <div style="clear:both"></div>

        <a id="btn-open-create-draft-dialog" href="#" class="btn-floating btn-large waves-effect waves-light"><i class="fas fa-plus"></i></a>

        <div id="dialog-add-widget-to-dashup" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.addWidgetToDashup" /></h4>
                    <div class="input-field col s12 m6">
                        <select id="section-dropdown">
                            <option value="-1"><fmt:message key="i18n.newSection" /></option>
                            <c:forEach items="${sections}" var="section">
                                <option value="${fn:escapeXml(section.id)}">${fn:escapeXml(section.name)}</option>
                            </c:forEach>
                        </select>
                        <label><fmt:message key="i18n.sections" /></label>
                    </div>
                    <div class="input-field col s12 m6">
                        <select name="size" id="add-widget-size-dropdown">
                            <option value="small" selected="selected"><fmt:message key="i18n.small" /></option>
                            <option value="medium"><fmt:message key="i18n.medium" /></option>
                            <option value="large"><fmt:message key="i18n.large" /></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-add-draft" class="btn modal-close waves-effect waves-light"><fmt:message key="i18n.ok" /></a>
                <a id="btn-cancel-add-draft" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>

            </div>
        </div>
        <div id="dialog-create-draft" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.createDraft" /></h4>
                    <div class="input-field col s12">
                        <input id="text-field-new-draft-name" type="text" class="validate">
                        <label for="text-field-draft-name"><fmt:message key="i18n.draftName" /></label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-create-draft" class="btn modal-close waves-effect waves-light"><fmt:message key="i18n.ok" /></a>
                <a id="btn-cancel-create-draft" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
            </div>
        </div>
        <div id="dialog-delete-draft" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.deleteDraft" /></h4>
                    <p><fmt:message key="i18n.confirmDeleteDraft" /></p>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-delete-draft" class="btn modal-close waves-effect waves-light"><fmt:message key="i18n.ok" /></a>
                <a id="btn-cancel-delete-draft" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
            </div>
        </div>
        <div id="dialog-publish-draft" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.publish" /></h4>
                    <p><fmt:message key="i18n.confirmPublishDraft" /></p>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-publish-draft" class="btn modal-close waves-effect waves-light"><fmt:message key="i18n.ok" /></a>
                <a id="btn-cancel-publish-draft" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
            </div>
        </div>
    </body>

    <jsp:include page="includes/webComponents.jsp" />
    <script type="text/javascript">
        const tags = [
            <c:forEach items="${tags}" var="tag">
            {name: "${tag.text}", id: ${tag.id}},
            </c:forEach>
        ];

        let propertiesToDelete = [];

        $( document ).ready(function () {
            $("#nav-item-workbench").parent().addClass("active");

            let toastOptions = {};
            switch (getAnchor()) {
                case "undoComplete":
                    toastOptions = {
                        html: "<fmt:message key="i18n.undoComplete" />",
                        classes: "success"
                    };
                    break;
                case "deletedDraft":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successDeletedDraft" />",
                        classes: "success"
                    };
                    break;
                case "changedInformation":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successChangedInformation" />",
                        classes: "success"
                    };
                    break;
                case "changedCode":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successChangedCode" />",
                        classes: "success"
                    };
                    break;
                case "draftNotValid":
                    toastOptions = {
                        html: "<fmt:message key="i18n.warningDraftNotValid" />",
                        classes: "warning"
                    };
                    break;
                case "draftCodeNotValid":
                    toastOptions = {
                        html: "<fmt:message key="i18n.warningDraftCodeNotValid" />",
                        classes: "warning"
                    };
                    break;
                case "addedWidget":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successAddedWidget" />",
                        classes: "success"
                    };
                    break;
                case "publishedDraft":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successPublishedDraft" />",
                        classes: "success"
                    };
                    break;
                case "changedProperties":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successChangedProperties" />",
                        classes: "success"
                    };
                    break;
            }
            if (getAnchor() !== null && getAnchor() !== "") {
                M.toast(toastOptions);
                clearAnchor()
            }

            $("aside").css("height", window.innerHeight + "px");

            let isPublished = document.URL.includes("published") ? "published" : "draft";

            $("#textarea-short-description").characterCounter();

            $(".tabs").tabs({
                swipeable: false
            });

            let tagsChips = M.Chips.init(document.getElementById("chips-tags"), {
                autocompleteOptions: {
                    data: {
                        <c:forEach items="${tags}" var="tag">
                            "${tag.text}": null,
                        </c:forEach>
                    },
                    limit: Infinity,
                    minLength: 1
                },
                placeholder: "<fmt:message key="i18n.enterTags" />",
                onChipAdd: function (element, chip) {
                    if (isTagNameValid($(chip).text().substr(0, $(chip).text().length - 5)) !== false) {
                        replaceCloseIconOfTags();
                        return true;
                    }
                    $(chip).remove();
                },
                data: [
                    <c:forEach items="${currentTags}" var="tag">
                    {tag: "${tag}"},
                    </c:forEach>
                ]
            });

            replaceCloseIconOfTags();

            if ($(".tabs-content").length > 0) {
                $(".tabs-content").css({"height": (window.innerHeight - $(".tabs-content").offset().top) + "px"});
            }

            $("#size-dropdown").on("change", function () {
                $("div#code-container div.input-field").css("display", "none");
                $("div#code-container #textarea-code-" + $(this).val()).parent().css("display", "block");
                let textArea = $("#textarea-code-" + $(this).val());
                textArea.parent().parent().css("display", "block");
                $("#pre-view-container").html(textArea.val());
            });

            $("#textarea-code-small, #textarea-code-medium, #textarea-code-large").bind('input propertychange', function () {
                $("#pre-view-container").html(($(this).val()));
            });

            let createDraftDialog = M.Modal.getInstance(document.getElementById("dialog-create-draft"));
            $("#btn-open-create-draft-dialog").on("click", function () {
                createDraftDialog.open();
            });

            $("#btn-submit-create-draft").on("click", function () {
                let draftName = $("#text-field-new-draft-name").val();
                if (draftName !== null) {
                    PostRequest.getInstance().make("workbench/createDraft", {
                        draftName: draftName
                    });
                } else {
                    M.toast({
                        html: "<fmt:message key="i18n.errorFillInRequiredFields" />",
                        classes: "error"
                    })
                }
            });

            let deleteDraftDialog = M.Modal.getInstance(document.getElementById("dialog-delete-draft"));
            $("#btn-delete-draft").on("click", function () {
                deleteDraftDialog.open();
            });

            $("#btn-submit-delete-draft").on("click", function () {
                PostRequest.getInstance().make("workbench/" + isPublished + "/${fn:escapeXml(current.id)}/deleteDraft", {});
            });

            let publishDraftDialog = M.Modal.getInstance(document.getElementById("dialog-publish-draft"));
            $("#btn-publish-widget").on("click", function () {
                publishDraftDialog.open();
            });

            $("#btn-submit-publish-draft").on("click", function () {
                PostRequest.getInstance().make("workbench/draft/${fn:escapeXml(current.id)}/publishDraft", {})
            });

            let addDraftDialog = M.Modal.getInstance(document.getElementById("dialog-add-widget-to-dashup"));
            $("#btn-add-widget").on("click", function () {
                addDraftDialog.open();
            });

            $("#btn-submit-add-draft").on("click", function () {
                PostRequest.getInstance().make("workbench/" + isPublished + "/${fn:escapeXml(current.id)}/addDraft", {
                    sectionId: $("#section-dropdown").val(),
                    size: $("#add-widget-size-dropdown").val()
                });
            });

            $("#btn-save-draft-information").on("click", function () {
                let shortDescription = $("#textarea-short-description").val();
                if (shortDescription.length < 256) {
                    PostRequest.getInstance().make("workbench/" + isPublished + "/${fn:escapeXml(current.id)}/changeInformation", {
                        category: $("#category-dropdown").val(),
                        draftName: $("#text-field-draft-name").val(),
                        shortDescription: shortDescription,
                        description: $("#textarea-description").val(),
                        tags: encodeURIComponent(JSON.stringify(parseTags(tags, tagsChips.getData())))
                    });
                } else {
                    M.toast({
                        html: "<fmt:message key="i18n.errorSortDescriptionTooLong"/>",
                        classes: "error"
                    });
                }
            });

            $("#btn-save-code").on("click", function () {
                let parameters = {},
                    size = $("#size-dropdown").val();
                parameters["code_" + size] = encodeURIComponent($("#textarea-code-" + size).val());
                PostRequest.getInstance().make("/workbench/" + isPublished + "/${fn:escapeXml(current.id)}/changeCode", parameters);
            });

            $("#btn-add-property").on("click", function () {
                let row = `<div class="row property-row" data-property-id="-1">
                                <div class="col input-field s3 m3">
                                    <input id="text-field-property-name" type="text" class="validate" />
                                    <label for="text-field-property-name"><fmt:message key="i18n.propertyName"/></label>
                                </div>
                                <div class="col input-field s3 m3">
                                    <input id="text-field-property" type="text" class="validate" />
                                    <label for="text-field-property"><fmt:message key="i18n.property"/></label>
                                </div>
                                <div class="col input-field s2 m2">
                                    <select id="text-field-property-type">
                                        <c:forEach items="${propertyTypes}" var="type" >
                                            <option value="${type.name}"><fmt:message key="i18n.${type.name}"/></option>
                                        </c:forEach>
                                    </select>
                                    <label><fmt:message key="i18n.propertyType"/></label>
                                </div>
                                <div class="col input-field s3 m3">
                                    <input id="text-field-property-default-value" type="text" class="validate" />
                                    <label for="text-field-property-default-value"><fmt:message key="i18n.defaultValue"/></label>
                                </div>
                                <div class="col input-field s1 m1">
                                    <a class="btn-delete-property waves-effect btn-flat">
                                        <i class="fas fa-times" style="color: var(--color-error)"></i>
                                    </a>
                                </div>
                            </div>`;
                $(".properties-container .row:first-child").before(row);
                $("select").formSelect();
            });

            $("#btn-update-properties").on("click", function () {
                let properties = [];
                $(".properties-container .property-row").each(function () {
                    properties.push({
                        id: $(this).attr("data-property-id"),
                        property: $(this).find("#text-field-property").val(),
                        name: $(this).find("#text-field-property-name").val(),
                        type: $(this).find("#text-field-property-type").val(),
                        defaultValue: $(this).find("#text-field-property-default-value").val()
                    });
                });
                PostRequest.getInstance().make("/workbench/" + isPublished + "/${fn:escapeXml(current.id)}/updateProperties", {
                    properties: encodeURIComponent(JSON.stringify(properties)),
                    propertiesToDelete: encodeURIComponent(JSON.stringify(propertiesToDelete))
                });
            })
        });

        $(document).delegate('.code-textarea', 'keydown', function(e) {
            let keyCode = e.keyCode || e.which;

            if (keyCode === 9) {
                e.preventDefault();
                let start = this.selectionStart;
                let end = this.selectionEnd;

                // set textarea value to: text before caret + tab + text after caret
                $(this).val($(this).val().substring(0, start)
                    + "\t"
                    + $(this).val().substring(end));

                // put caret at right position again
                this.selectionStart =
                    this.selectionEnd = start + 1;
            }
        });

        $(document).on("click", ".btn-delete-property", function () {
            let row = $(this).parent().parent(),
                propertiesId = row.attr("data-property-id");
            if (propertiesId > 0) {
                propertiesToDelete.push(propertiesId);
            }
            row.remove();
        });

        function replaceCloseIconOfTags() {
            $(".material-icons.close").addClass("fas fa-times").html("");
        }

        function parseTags(tags, editedTags) {
            let result = [];
            editedTags.forEach(function (chipTag) {
                let tag = isTagNameValid(chipTag.tag);
                if (tag !== false) {
                    result.push(tag);
                }
            });
            return result;
        }

        function isTagNameValid(tagName) {
            let result = false;
            tags.forEach(function (tag) {
                if (tag.name === tagName) {
                    result = tag;
                }
            });
            return result;
        }
    </script>
</html>