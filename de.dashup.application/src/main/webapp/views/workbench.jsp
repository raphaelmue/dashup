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
                    <c:if test="${fn:escapeXml(currentDraft.id) > 0}">
                        <a class="breadcrumb" href="${fn:escapeXml(pageContext.request.contextPath)}/workbench/${fn:escapeXml(currentDraft.id)}">
                            ${fn:escapeXml(currentDraft.name)}
                        </a>
                    </c:if>
                </div>
            </div>
        </nav>
        <aside>
            <h4><fmt:message key="i18n.workbench" /></h4>
            <ul>
                <c:forEach items="${drafts}" var="draft">
                    <li>
                        <a href="${fn:escapeXml(pageContext.request.contextPath)}/workbench/${fn:escapeXml(draft.id)}/" class="waves-effect <c:if test="${draft.id == currentDraft.id}">active</c:if>">${fn:escapeXml(draft.name)}</a>
                    </li>
                </c:forEach>
            </ul>
            <a id="btn-open-create-draft-dialog" href="#" class="btn-floating btn-large waves-effect waves-light"><i class="fas fa-plus"></i></a>
        </aside>
        <main>
            <c:choose>
                <c:when test="${fn:escapeXml(currentDraft.id) > 0}">
                    <div class="container">
                        <div class="row">
                            <button id="btn-delete-draft" class="btn-flat waves-effect">
                                <i class="fas fa-trash-alt"></i>
                                <fmt:message key="i18n.delete"/>
                            </button>
                            <button id="btn-publish-widget" class="btn-flat waves-effect">
                                <i class="fas fa-truck"></i>
                                <fmt:message key="i18n.publish"/>
                            </button>
                            <button id="btn-add-widget" class="btn-flat waves-effect">
                                <i class="fas fa-plus"></i>
                                <fmt:message key="i18n.add"/>
                            </button>
                        </div>
                        <div class="row z-depth-1" style="margin: 0">
                            <div class="col s12">
                                <ul class="tabs" style="margin: 0">
                                    <li class="tab"><a class="active" href="#code"><fmt:message key="i18n.code" /></a></li>
                                    <li class="tab"><a href="#basicInformation"><fmt:message key="i18n.basicInformation" /></a></li>
                                </ul>
                            </div>
                            <div id="code" class="col s12">
                                <div class="row">
                                    <div class="col s12 m12">
                                        <h4 style="margin-bottom: 20px;"><fmt:message key="i18n.preview" /></h4>
                                        <div class="col card m2 s6" style="float: none; margin: 0 auto;">
                                            <div class="card-content" id="pre-view-container">${currentDraft.codeSmall}</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12">
                                        <h4 style="margin-bottom: 20px;"><fmt:message key="i18n.code" /></h4>
                                        <div class="row">
                                            <div class="col s12 m12 input-field">
                                                <select name="size" id="size-dropdown">
                                                    <option value="small" selected="selected"><fmt:message key="i18n.small" /></option>
                                                    <option value="medium"><fmt:message key="i18n.medium" /></option>
                                                    <option value="large"><fmt:message key="i18n.large" /></option>
                                                </select>
                                                <label><fmt:message key="i18n.size" /></label>
                                            </div>
                                            <div class="col s12 m12" id="code-container">
                                                <div class="input-field">
                                                    <textarea id="textarea-code-small" class="materialize-textarea">${fn:escapeXml(currentDraft.codeSmall)}</textarea>
                                                    <label for="textarea-code-small"><fmt:message key="i18n.code" /></label>
                                                </div>
                                                <div class="input-field" style="display: none;">
                                                    <textarea id="textarea-code-medium" class="materialize-textarea">${fn:escapeXml(currentDraft.codeMedium)}</textarea>
                                                    <label for="textarea-code-medium"><fmt:message key="i18n.code" /></label>
                                                </div>
                                                <div class="input-field" style="display: none;">
                                                    <textarea id="textarea-code-large" class="materialize-textarea">${fn:escapeXml(currentDraft.codeLarge)}</textarea>
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
                                        <button id="btn-undo-code" class="btn-flat waves-effect">
                                            <i class="fas fa-times"></i>
                                            <fmt:message key="i18n.undo"/>
                                        </button>
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
                                               value="${fn:escapeXml(currentDraft.name)}"/>
                                        <label for="text-field-draft-name"><fmt:message
                                                key="i18n.draftName"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 m12">
                                            <textarea id="textarea-short-description" name="shortDescription"
                                                      class="materialize-textarea">${fn:escapeXml(currentDraft.shortDescription)}</textarea>
                                        <label for="textarea-short-description"><fmt:message
                                                key="i18n.shortDescription"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12 m12">
                                            <textarea id="textarea-description" name="description"
                                                      class="materialize-textarea">${fn:escapeXml(currentDraft.description)}</textarea>
                                        <label for="textarea-description"><fmt:message
                                                key="i18n.description"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <button id="btn-save-draft-information" class="btn waves-effect waves-light"
                                                type="submit">
                                            <i class="fas fa-check"></i>
                                            <fmt:message key="i18n.save"/>
                                        </button>
                                        <a id="btn-undo-draft-information" class="btn-flat waves-effect">
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
                    <div style="text-align: center; margin: 30% auto">
                        <h5 style="text-align: center;">
                            <i style="font-size: 50px; margin-bottom: 20px;" class="fas fa-drafting-compass"></i>
                            <br />
                            <fmt:message key="i18n.selectDraft" />
                        </h5>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

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
    </body>
    <script type="text/javascript">
        $( document ).ready(function () {
            $("#nav-item-workbench").parent().addClass("active");

            let toastOptions = {};
            switch (getAnchor()) {
                case "deletedDraft":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successDeletedDraft" />",
                        classes: "success"
                    };
                    break;
                case "changedInformation":
                    toastOptions = {
                        html: "<fmt:message key="i18n.dataSuccessfullySaved" />",
                        classes: "success"
                    };
                    break;
                case "changedCode":
                    toastOptions = {
                        html: "<fmt:message key="i18n.successChangedCode" />",
                        classes: "success"
                    };
                    break;
            }
            if (getAnchor() !== null && getAnchor() !== "") {
                M.toast(toastOptions);
                clearAnchor()
            }

            $(".tabs").tabs({
                swipeable: true
            });

            if ($(".tabs-content").length > 0) {
                $(".tabs-content").css({ "height": (window.innerHeight - $(".tabs-content").offset().top) + "px"});
            }

            $("#size-dropdown").on("change", function () {
                $("div#code-container div.input-field").css("display", "none");
                let textArea = $("#textarea-code-" + $(this).val());
                textArea.parent().css("display", "block");
                updatePreviewContainer(textArea.val(), textArea.attr("id"));
            });

            $("#textarea-code-small, #textarea-code-medium, #textarea-code-large").bind('input propertychange', function() {
                updatePreviewContainer($(this).val(), $(this).attr("id"));
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
                PostRequest.getInstance().make("/workbench/${fn:escapeXml(currentDraft.id)}/deleteDraft", {});
            });

            $("#btn-save-draft-information").on("click", function() {
                PostRequest.getInstance().make("/workbench/${fn:escapeXml(currentDraft.id)}/changeInformation", {
                    draftName: $("#text-field-draft-name").val(),
                    shortDescription: $("#textarea-short-description").val(),
                    description: $("#textarea-description").val()
                });
            });

            $("#btn-save-code").on("click", function () {
                let parameters = {},
                    size = $("#size-dropdown").val();
                parameters["code_" + size] = encodeURIComponent($("#textarea-code-" + size).val())
                PostRequest.getInstance().make("/workbench/${fn:escapeXml(currentDraft.id)}/changeCode", parameters);
            });
        });

        function updatePreviewContainer(html, id) {
            let previewContainer = $("#pre-view-container");
            previewContainer.html(html);

            previewContainer.parent().removeClass();
            previewContainer.parent().addClass("col card");
            switch (id.substr(id.lastIndexOf("-") + 1)) {
                case "small":
                    previewContainer.parent().addClass("m2 s6");
                    break;
                case "medium":
                    previewContainer.parent().addClass("m4 s12");
                    break;
                case "large":
                    previewContainer.parent().addClass("m6 s12");
                    break;
            }

        }
    </script>
</html>