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
                    <a href="${pageContext.request.contextPath}/" class="breadcrumb">dashup</a>
                    <a href="${pageContext.request.contextPath}/marketplace/" class="breadcrumb"><fmt:message key="i18n.marketplace" /></a>
                    <a href="#" class="breadcrumb"><fmt:message key="i18n.searchResult" /></a>
                </div>
            </div>
        </nav>
        <div class="row" style="margin-top: 25px">
                <div class="col m4 offset-m4 s8 offset-s1">
                    <div class="input-field">
                        <input id="text-field-search" name="search" type="text" class="validate">
                        <label for="text-field-search"><fmt:message key="i18n.enterSearchterm"/></label>
                    </div>
                </div>
                <div class="col m2 s2" style="margin-top: 18px">
                    <a class='waves-effect waves-light modal-trigger' href='#widget-filter'>
                        <i class="colored-text fas fa-filter"></i>
                    </a>
                    <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" onclick="onSearch()"
                            name="search">
                        <fmt:message key="i18n.go" />
                    </button>
                </div>

        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <hr/>
                <ul class="collection">

                    <c:if test="${empty widgets}">
                        <li class="collection-item" id="empty">
                            <div class="row">
                                <div class="col m4 s4">
                                    <h5><fmt:message key="i18n.noResultFound"/></h5>
                                </div>
                            </div>
                        </li>
                    </c:if>

                    <c:forEach items="${widgets}" var="widget">
                        <li class="collection-item" id="${fn:escapeXml(widget.id)}" data-widget-name="${fn:escapeXml(widget.name)}">
                            <div class="row">
                                <div class="col m3 s3">
                                    <i class="fas fa-${fn:escapeXml(widget.iconCode)} fa-7x"></i>
                                </div>
                                <div class="col m4 s4">

                                    <a href="/marketplace/detailView/${fn:escapeXml(widget.id)}" class="clickable">
                                        <h5>${fn:escapeXml(widget.name)}</h5>
                                        <div>${fn:escapeXml(widget.shortDescription)}</div>
                                        <div class="star-rating" style="font-size: 25px; margin-top: 15px; color: var(--color-dark-gray);">
                                            <div class="back-stars">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <div class="front-stars"
                                                     style="width:  ${fn:escapeXml(widget.averageRating)}%;color: var(--color-primary);">
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <div class="col m2 offset-m3" style="margin-top: 30px">
                                    <button id="btn-add" class="btn waves-effect waves-light" type="submit" name="search" data-widget="${fn:escapeXml(widget.id)}" onclick="onAdd(event)">
                                        <fmt:message key="i18n.add"/>
                                    </button>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div id="dialog-add-panel-to-dashup" class="modal">
            <div class="modal-content">
                <div class="row">
                    <h4 id="widget-name"></h4>
                    <h5><fmt:message key="i18n.addPanelToDashup"/></h5>
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
                <a id="btn-add-widget" class="modal-close btn waves-effect waves-light"><fmt:message
                        key="i18n.ok"/></a>
                <a id="btn-cancel-add-widget" class="btn-flat modal-close waves-effect"><fmt:message key="i18n.cancel" /></a>
            </div>
        </div>

        <!-- Modal Structure -->
        <div id="widget-filter" class="modal">
            <div class="modal-content">
                <div class="row">
                    <div class="col s12 m6 ">

                        <div class="row">
                            <h5>Filter Options</h5>
                        </div>

                        <div class="row">
                            <div class="col s4 m6 valign-wrapper">
                                <h6><fmt:message key="i18n.tags"/></h6>
                            </div>
                            <div class="col s8 m6 valign-wrapper">
                                <div id="chips-tags" class="chips chips-placeholder input-field">
                                    <input class="input" placeholder=<fmt:message key="i18n.enterTags"/>>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s4 m6 valign-wrapper">
                                <h6><fmt:message key="i18n.rating"/></h6>
                            </div>
                            <div class="col s8 m6 valign-wrapper">
                                <div class="star-rating"
                                     style="font-size: 25px; margin-top: 15px; color: var(--color-dark-gray);">
                                    <div class="back-stars star">
                                        <i id="i1" class="fa fa-star" aria-hidden="true"></i>
                                        <i id="i2" class="fa fa-star" aria-hidden="true"></i>
                                        <i id="i3" class="fa fa-star" aria-hidden="true"></i>
                                        <i id="i4" class="fa fa-star" aria-hidden="true"></i>
                                        <i id="i5" class="fa fa-star" aria-hidden="true"></i>
                                        <div id="front-stars" class="front-stars star"
                                             style="width:  20%; color: var(--color-primary);">
                                            <i id="f1" class="fa fa-star" aria-hidden="true"></i>
                                            <i id="f2" class="fa fa-star" aria-hidden="true"></i>
                                            <i id="f3" class="fa fa-star" aria-hidden="true"></i>
                                            <i id="f4" class="fa fa-star" aria-hidden="true"></i>
                                            <i id="f5" class="fa fa-star" aria-hidden="true"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s4 m6 valign-wrapper">
                                <h6><fmt:message key="i18n.category"/></h6>
                            </div>
                            <div class="col s8 m6 valign-wrapper">
                                <div id="chips-categories" class="chips chips-placeholder input-field">
                                    <input class="input" placeholder=<fmt:message key="i18n.selectCategory"/>>
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col s4 m6 valign-wrapper">
                                <h6><fmt:message key="i18n.publicationDate"/></h6>
                            </div>
                            <div class="col s8 m6 valign-wrapper">

                                <label for="text-field-filter-publication-date"></label><input
                                    id="text-field-filter-publication-date" name="publicationDate" type="text"
                                    class="datepicker"
                                    value="<fmt:message key="i18n.showMore" />"/>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-close waves-effect waves-green btn-flat">OK</a>
                </div>
            </div>
        </div>
    </body>
<script type="text/javascript">
        let rating = localStorage.getItem("rating");
        let currentWidget;

        $( document ).ready(function () {

            $('.chips').chips();
            $('.chips-placeholder').chips({
                secondaryPlaceholder: '+' + '<fmt:message key="i18n.tag" />'
            });

            $('#chips-categories').chips({
                placeholder: '<fmt:message key="i18n.selectCategory" />',
                secondaryPlaceholder: '+' + '<fmt:message key="i18n.category" />'
            });

            $("#weather-panel").on("click", function () {
                PostRequest.getInstance().make("marketplace/detailView/1", {
                });
            });

            $("#text-field-filter-publication-date").datepicker({
                format: "yyyy-mm-dd",
                yearRange: [2019, 2019],
                defaultDate: localStorage.getItem("date"),
                setDefaultDate: true,
            });

            $(".star").on("click", function (element) {
                let starId = element.target.id;
                let factor = starId.substr(1, 1);
                rating = factor * 20;

                let frontStars = document.getElementById("front-stars");
                frontStars.style.width = rating + "%";
            });

            let frontStars = document.getElementById("front-stars");
            frontStars.style.width = rating + "%";

            let datepickerElement = document.getElementById("text-field-filter-publication-date");
            datepickerElement.setAttribute("value",localStorage.getItem("date"));

            let tagsInstance = M.Chips.getInstance($('#chips-tags'));
            let categoriesInstance = M.Chips.getInstance($('#chips-categories'));

            let tagsLocal = JSON.parse(localStorage.getItem("tags"));
            let categoriesLocal = JSON.parse(localStorage.getItem("categories"));

            tagsLocal.forEach(tag => tagsInstance.addChip(tag));
            categoriesLocal.forEach(category => categoriesInstance.addChip(category));

            //add widget to dashup
            $('#btn-add-widget').on('click',function () {
                let sectionId = $('#section-dropdown').val();
                let widgetSize = $('#add-widget-size-dropdown').val();
                if(currentWidget){
                    PostRequest.getInstance().make("marketplace/detailView/"+currentWidget+"/addWidgetToPersonalDashup",{
                        sectionId: sectionId,
                        widgetSize: widgetSize,
                    });
                }
            });
        });

        function onAdd(widgetId) {
            currentWidget = widgetId.srcElement.getAttribute("data-widget");
            let widgetListItem = document.getElementById(currentWidget);
            let widgetName = document.getElementById("widget-name");
            widgetName.innerText = widgetListItem.getAttribute("data-widget-name");

            let dialogAddPanel = document.getElementById("dialog-add-panel-to-dashup");
            let chooseSectionToAddToDialog = M.Modal.getInstance(dialogAddPanel);
            chooseSectionToAddToDialog.open();
        }

        function onSearch() {
            let searchField = document.getElementById("text-field-search");
            let instance = M.Datepicker.getInstance(document.getElementById("text-field-filter-publication-date"));
            let date = instance.toString();
            if(date === ""){
                date = "2019-01-01";
            }

            let tags = M.Chips.getInstance($('#chips-tags')).chipsData;
            let categories = M.Chips.getInstance($('#chips-categories')).chipsData;
            localStorage.setItem("date",instance.toString());
            localStorage.setItem("rating",rating);
            localStorage.setItem("categories", JSON.stringify(categories));
            localStorage.setItem("tags",JSON.stringify(tags));
            let url = "${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search?" + $.param({searchQuery:searchField.value,date,rating});
            tags.forEach(tag => url+="&tags=" + tag.tag);
            categories.forEach(category => url+="&categories=" + category.tag);
            window.location.replace(url);
        }
    </script>
</html>
