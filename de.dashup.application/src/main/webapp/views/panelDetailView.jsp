<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp"/>
    <body>
        <jsp:include page="includes/header.jsp"/>
        <nav>
            <div class="nav-wrapper">
                <div class="col s12">
                    <a href="${pageContext.request.contextPath}/" class="breadcrumb">dashup</a>
                    <a href="/marketplace/" class="breadcrumb"><fmt:message key="i18n.marketplace"/></a>
                    <a href="/marketplace/detailView/${fn:escapeXml(widget.id)}" class="breadcrumb">${fn:escapeXml(widget.name)}</a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col" style="margin-top: 2%">
                    <i class="fas fa-${fn:escapeXml(widget.iconCode)} fa-10x"></i>
                </div>
                <div class="col m7">
                    <div class="row">
                        <div class="col m12">
                            <h2>${fn:escapeXml(widget.name)}</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col m12">
                            ${fn:escapeXml(widget.shortDescription)}
                        </div>
                    </div>
                    <div class="row">
                        <div class="col m12">
                            <div class="star-rating">
                                <div class="back-stars">
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <div class="front-stars" style="width:  ${fn:escapeXml(widget.averageRating)}%">
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col m2" style="margin-top: 40px">
                    <div class="row">
                        <button id="btn-add-panel-to-dashup" class="btn waves-effect waves-light" type="submit"
                                name="add">
                            <fmt:message key="i18n.add"/>
                        </button>
                    </div>
                    <div class="row">
                        <button id="btn-add-comment" class="btn waves-effect waves-light" type="submit"
                            name="comment">
                            <fmt:message key="i18n.addComment"/>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col s12 l12 m12">
                    <ul id="tabs-swipe-demo" class="tabs tabs-fixed-width">
                        <li class="tab col s3"><a class="active" href="#overview-tab">Overview</a></li>
                        <li class="tab col s3"><a href="#ratings-tab">Ratings</a></li>
                        <li class="tab col s3"><a href="#similar-tab">Similar</a></li>
                    </ul>
                    <div id="overview-tab" class="col s12">
                        <div class="row" style="margin-top: 10px;">
                            <div class="col m8" align="justify">
                                ${fn:escapeXml(widget.description)}
                            </div>
                            <div class="col m4">
                                <blockquote style="color: var(--color-primary)">
                                    <div class="row">
                                        <h4><fmt:message key="i18n.additionalInformation"/></h4>
                                    </div>
                                    <div class="row">
                                        <b><fmt:message key="i18n.dateOfPublication"/></b>
                                    </div>
                                    <div class="row">
                                        ${fn:escapeXml(widget.publicationDate)}
                                    </div>
                                    <div class="row">
                                        <b><fmt:message key="i18n.publisherId"/></b>
                                    </div>
                                    <div class="row">
                                        ${fn:escapeXml(publisher.userName)}
                                    </div>
                                    <div class="row">
                                        <b><fmt:message key="i18n.category"/></b>
                                    </div>
                                    <div class="row">
                                        ${fn:escapeXml(widget.category)}
                                    </div>
                                    <div class="row">
                                        <b><fmt:message key="i18n.tags"/></b>
                                    </div>
                                    <c:if test="${fn:length(tags) > 0}">
                                        <div class="row">
                                            <c:forEach items="${tags}" var="tag">
                                                <div class="chip">
                                                        ${fn:escapeXml(tag)}
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                </blockquote>
                            </div>
                        </div>
                    </div>
                    <div id="ratings-tab" class="col s12 l12 m12">
                        <div class="row" style="margin-top: 10px;">
                            <ul class="collection">
                                <c:forEach items="${ratings}" var="rating">
                                    <li class="collection-item">
                                        <div class="row" style="margin-bottom: 0px">
                                            <div class="col m4">
                                                <h5>${fn:escapeXml(rating.title)}</h5>
                                            </div>
                                            <div class="col m2 offset-m6">
                                                <div class="star-rating" style="font-size: 25px; margin-top: 15px;">
                                                    <div class="back-stars">
                                                        <i class="fa fa-star" aria-hidden="true"></i>
                                                        <i class="fa fa-star" aria-hidden="true"></i>
                                                        <i class="fa fa-star" aria-hidden="true"></i>
                                                        <i class="fa fa-star" aria-hidden="true"></i>
                                                        <i class="fa fa-star" aria-hidden="true"></i>
                                                        <div class="front-stars"
                                                             style="width:  ${fn:escapeXml(rating.rating)}%;">
                                                            <i class="fa fa-star" aria-hidden="true"></i>
                                                            <i class="fa fa-star" aria-hidden="true"></i>
                                                            <i class="fa fa-star" aria-hidden="true"></i>
                                                            <i class="fa fa-star" aria-hidden="true"></i>
                                                            <i class="fa fa-star" aria-hidden="true"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="font-size: 15px; margin-left: 3px">
                                            By ${fn:escapeXml(rating.userName)} ${fn:escapeXml(rating.userSurname)}, Last
                                            changed on ${fn:escapeXml(rating.changeDate)}
                                        </div>
                                        <div class="row" style="margin-left: 3px">
                                                ${fn:escapeXml(rating.text)}
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div id="similar-tab" class="col s12 l12 m12">
                        <div class="row" style="margin-top: 10px;">
                            <c:forEach items="${similarWidgets}" var="panel">
                                <div class="col m4">
                                    <div class="card horizontal" style="height: 230px">
                                        <div class="card-stacked">
                                            <div class="card-content">
                                                <div class="row">
                                                    <div class="col">
                                                        <i class="fas fa-${fn:escapeXml(panel.iconCode)} fa-3x"></i>
                                                    </div>
                                                    <div class="col">
                                                        <h5 style="margin-top: 10px;">${fn:escapeXml(panel.name)}</h5>
                                                    </div>
                                                </div>
                                                    ${fn:escapeXml(panel.shortDescription)}
                                            </div>
                                            <div class="card-action">
                                                <a href="/marketplace/detailView/${panel.id}"><fmt:message key="i18n.showMore"/></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="dialog-add-panel-to-dashup" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.addPanelToDashup"/></h4>
                    <div class="input-field col s12 m6">
                        <select id="section-dropdown">
                            <option value="-1"><fmt:message key="i18n.newSection"/></option>
                            <c:forEach items="${sections}" var="section">
                                <option value="${fn:escapeXml(section.id)}">${fn:escapeXml(section.name)}</option>
                            </c:forEach>
                        </select>
                        <label><fmt:message key="i18n.sections"/></label>
                    </div>
                    <div class="input-field col s12 m6">
                        <select name="size" id="add-widget-size-dropdown">
                            <option value="small" selected="selected"><fmt:message key="i18n.small"/></option>
                            <option value="medium"><fmt:message key="i18n.medium"/></option>
                            <option value="large"><fmt:message key="i18n.large"/></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-add-widget" class="modal-close waves-effect btn"><fmt:message
                        key="i18n.ok"/></a>
                <a id="btn-cancel-add-widget" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
            </div>
        </div>
        <div id="dialog-add-comment" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.addComment"/></h4>
                </div>
                <div class="row">
                    <div class="input-field col s12 m12" style="margin-top: 0">
                        <input id="text-field-title-new-rating" name="title" type="text" class="validate"/>
                        <label for="text-field-title-new-rating"><fmt:message key="i18n.title"/></label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12 m12" style="margin-top: 0">
                        <textarea id="text-field-add-new-comment" name="newComment" class="materialize-textarea"></textarea>
                        <label for="text-field-add-new-comment"><fmt:message key="i18n.newComment"/></label>
                    </div>
                </div>
                <div class="row">
                    <div class="col m3">
                        <div class="star-rating" style="margin-top: 10px">
                            <div id="star-rating-new-comment" class="back-stars">
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <i class="fa fa-star" aria-hidden="true"></i>
                                <div id="star-rating-new-comment-front" class="front-stars" style="width: 0">
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                    <i class="fa fa-star" aria-hidden="true"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col m2" style="margin-top: 10px; font-size: 16px">
                        <p>
                            <fmt:message key="i18n.yourRating"/>:
                            <span id="label-percentage-value-new-rating" style="color: var(--color-dark-gray)">0%</span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-comment" class="modal-close waves-effect btn"><fmt:message
                        key="i18n.ok"/></a>
                <a id="btn-cancel-comment" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
            </div>
        </div>
    </body>
    <script>
        $(document).ready(function () {
            //anchor handling
            let toastOptions = {}
            //needed cause tabs are working with anchor too and if this anchor is removed tabs will stop working
            let foreignAnchor = false;
            switch (getAnchor()) {
                case "addedRating":
                    toastOptions = {
                        html: "<fmt:message key="i18n.addedRating" />",
                        classes: "success"
                    };
                    break;
                case "failedToAddRating":
                    toastOptions = {
                        html: "<fmt:message key="i18n.failedToAddRating" />",
                        classes: "error"
                    };
                    break;
                case "addedWidget":
                    toastOptions = {
                        html: "<fmt:message key="i18n.addedWidget" />",
                        classes: "success"
                    };
                    break;
                case "faieldToAddWidget":
                    toastOptions = {
                        html: "<fmt:message key="i18n.failedToAddWidget" />",
                        classes: "error"
                    };
                    break;
                default:
                    foreignAnchor=true;
                    break;
                    }

                    if (getAnchor() !== null && getAnchor() !== "" && !foreignAnchor) {
                        M.toast(toastOptions);
                        clearAnchor()
                    }

            //Init
            let starsAreMovable = true;
            let currentRatingPercentage = 0;
            $('.tabs').tabs();
            $('.chips').chips();
            $("#nav-item-marketplace").parent().addClass("active");

            let chooseSectionToAddToDialog = M.Modal.getInstance(document.getElementById("dialog-add-panel-to-dashup"));
            $('#btn-add-panel-to-dashup').on("click", function () {
                chooseSectionToAddToDialog.open();
            });

            let addCommentForPanelDialog = M.Modal.getInstance(document.getElementById("dialog-add-comment"));
            $('#btn-add-comment').on("click", function () {
                addCommentForPanelDialog.open();
            });

            //star rating for new comment
            $('#star-rating-new-comment').on('mousemove', function(e){
                if (starsAreMovable) {
                    let offset = $('#star-rating-new-comment').offset();
                    currentRatingPercentage = (e.pageX - offset.left) / $('#star-rating-new-comment').width() * 100;

                    $('#star-rating-new-comment-front').css('width', currentRatingPercentage + '%');
                    $('#label-percentage-value-new-rating').html(Math.round(currentRatingPercentage)+'%');
                }
            });

            $('#star-rating-new-comment').on('click',function () {
                starsAreMovable = !starsAreMovable;
            });

            $('#btn-submit-comment').on('click',function () {
                let title = $('#text-field-title-new-rating').val();
                let text = $('#text-field-add-new-comment').val();
                if (title !== '' && text !== '') {
                    PostRequest.getInstance().make("marketplace/detailView/${fn:escapeXml(widget.id)}/addRating", {
                        title: title,
                        text: text,
                        rating: Math.round(currentRatingPercentage),
                    });
                }
            });
            //add widget to dashup
            $('#btn-add-widget').on('click',function () {
                let sectionId = $('#section-dropdown').val();
                let widgetSize = $('#add-widget-size-dropdown').val();
                    PostRequest.getInstance().make("marketplace/detailView/${fn:escapeXml(widget.id)}/addWidgetToPersonalDashup",{
                        sectionId: sectionId,
                        widgetSize: widgetSize,
                    });
            });
        });

    </script>
</html>
