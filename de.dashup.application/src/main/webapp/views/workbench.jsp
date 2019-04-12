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
                    <div class="row">
                        <div class="col ">
                            <ul class="tabs">
                                <li class="tab"><a class="active" href="#code"><fmt:message key="i18n.code" /></a></li>
                                <li class="tab"><a href="#basicInformation"><fmt:message key="i18n.basicInformation" /></a></li>
                            </ul>
                        </div>
                        <div id="code" class="col s12">
                            <div class="container">
                                <div class="row">
                                    <div class="col s12 m6">
                                        <h3 style="margin-bottom: 50px;"><fmt:message key="i18n.code" /></h3>
                                        <div class="input-field">
                                            <textarea id="textarea-code" class="materialize-textarea">${fn:escapeXml(currentDraft.code)}</textarea>
                                            <label for="textarea-code"><fmt:message key="i18n.code" /></label>
                                        </div>
                                    </div>
                                    <div class="col s12 m6">
                                        <h3 style="margin-bottom: 50px;"><fmt:message key="i18n.preview" /></h3>
                                        <div class="col card s12 m12" >
                                            <div class="card-content" id="pre-view-container">${currentDraft.code}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="basicInformation" class="col s12">
                            <div class="container">
                                <div class="row">
                                    <h3><fmt:message key="i18n.basicInformation"/></h3>
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

    </body>
    <script type="text/javascript">
        $( document ).ready(function () {
            $("#nav-item-workbench").parent().addClass("active");

            $(".tabs").tabs({
                swipeable: true
            });

            $(".carousel").css({ "height": ($("#basicInformation .container").height() + 55) + "px"});

            let toastOptions = {};
            switch (getAnchor()) {
                case "changedInformation":
                    toastOptions = {
                        html: "<fmt:message key="i18n.dataSuccessfullySaved" />",
                        classes: "success"
                    };
                    break;
            }
            if (getAnchor() !== null && getAnchor() !== "") {
                M.toast(toastOptions);
                clearAnchor()
            }

            $("#textarea-code").bind('input propertychange', function() {
                console.log("changed");
                console.log($("#textarea-code").val());

                $("#pre-view-container").html($("#textarea-code").val());
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

            $("#btn-save-draft-information").on("click", function() {
                PostRequest.getInstance().make("/workbench/${fn:escapeXml(currentDraft.id)}/changeInformation", {
                    draftName: $("#text-field-draft-name").val(),
                    shortDescription: $("#textarea-short-description").val(),
                    description: $("#textarea-description").val()
                })
            })
        });
    </script>
</html>