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
                    <a href="/marketplace/" class="breadcrumb">${fn:escapeXml(widget.name)}</a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col m2" style="margin-top: 2%; margin-left: 2%">
                    <i class="fas fa-cloud fa-10x"></i>
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
                    <button id="btn-add-panel-to-dashup" class="btn waves-effect waves-light" type="submit"
                                name="add">
                            <fmt:message key="i18n.add"/>
                    </button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col m10 offset-m1">
                <ul id="tabs-swipe-demo" class="tabs tabs-fixed-width">
                    <li class="tab col s3"><a class="active" href="#overview-tab">Overview</a></li>
                    <li class="tab col s3"><a href="#ratings-tab">Ratings</a></li>
                    <li class="tab col s3"><a href="#similar-tab">Similar</a></li>
                </ul>
                <div id="overview-tab" class="col s12">
                    <div class="row">
                        <div class="col m8">
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
                <div id="ratings-tab" class="col s12">
                    <div class="row">
                        <div class="col m8">
                            Maybe we want to sort the comments here?
                        </div>
                        <div class="col m2 offset-m2">
                            <button id="btn-add-comment" class="btn waves-effect waves-light" type="submit"
                                    name="comment">
                                <fmt:message key="i18n.addComment"/>
                            </button>
                        </div>
                    </div>
                    <div class="row">
                        <ul class="collection">
                            <c:forEach items="${ratings}" var="rating">
                                <li class="collection-item">
                                    <div class="row" style="margin-bottom: 0px">
                                        <div class="col m4">
                                            <h5>${fn:escapeXml(rating.title)}</h5>
                                        </div>
                                        <div class="col m2 offset-m6">
                                            <div class="star-rating" style="font-size: 25px; margin-top: 15px">
                                                <div class="back-stars">
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <div class="front-stars" style="width:  ${fn:escapeXml(rating.rating)}%">
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
                                        By ${fn:escapeXml(rating.userName)} ${fn:escapeXml(rating.userSurname)}, Last changed on ${fn:escapeXml(rating.changeDate)}
                                    </div>
                                    <div class="row" style="margin-left: 3px">
                                            ${fn:escapeXml(rating.text)}
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div id="similar-tab" class="col s12">

                </div>
            </div>
        </div>
        <div id="dialog-add-panel-to-dashup" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.addPanelToDashup" /></h4>
                    <div class="input-field col s12">
                        <select id="language-dropdown">
                            <option value="default"><fmt:message key="i18n.newSection" /></option>
                            <c:forEach items="${sections}" var="section">
                                <option value="${fn:escapeXml(section.id)}">${fn:escapeXml(section.name)}</option>
                            </c:forEach>
                        </select>
                        <label><fmt:message key="i18n.sections" /></label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="btn-submit-section-to-add-to" class="modal-close waves-effect waves-green btn-flat"><fmt:message key="i18n.ok" /></a>
            </div>
        </div>
        <div id="dialog-add-comment" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4><fmt:message key="i18n.addComment" /></h4>
                </div>
                <div class="row">
                    <div class="input-field col s12 m12" style="margin-top: 0">
                        <textarea id="text-field-add-new-comment" name="newComment" class="materialize-textarea"></textarea>
                        <label for="text-field-add-new-comment"><fmt:message key="i18n.newComment"/></label>
                    </div>
                </div>
                <div class="row">
                    <div class="star-rating">
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
            <div class="modal-footer">
                <a id="btn-submit-comment" class="modal-close waves-effect waves-green btn-flat"><fmt:message key="i18n.ok" /></a>
            </div>
        </div>
    </body>
    <script>
        $(document).ready(function () {
            var starsAreMovable = true;
            $('.tabs').tabs();
            $('.chips').chips();

            let chooseSectionToAddToDialog = M.Modal.getInstance(document.getElementById("dialog-add-panel-to-dashup"));
            $('#btn-add-panel-to-dashup').on("click", function () {
                chooseSectionToAddToDialog.open();
            });

            let addCommentForPanelDialog = M.Modal.getInstance(document.getElementById("dialog-add-comment"));
            $('#btn-add-comment').on("click", function () {
                addCommentForPanelDialog.open();
            });

            $('#star-rating-new-comment').on('mousemove', function(e){
                if (starsAreMovable) {
                    let offset = $('#star-rating-new-comment').offset();
                    let x = (e.pageX - offset.left) / $('#star-rating-new-comment').width() * 100;

                    $('#star-rating-new-comment-front').css('width', x + '%');
                }
            });

            $('#star-rating-new-comment').on('click',function () {
                starsAreMovable = !starsAreMovable;
            });
        });

    </script>
</html>
