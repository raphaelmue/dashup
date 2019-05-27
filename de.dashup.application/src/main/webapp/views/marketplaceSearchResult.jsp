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
                    <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" onclick="onSearch()"
                            name="search">
                        <fmt:message key="i18n.go" />
                    </button>
                </div>

        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <div class="col s1 valign-wrapper">
                    <h6>Rating</h6>
                </div>
                <div class="col s3">
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
                <div class="col s2 valign-wrapper">
                    <h6>Publication Date</h6>
                </div>
                <div class="col s5">

                    <label for="text-field-filter-publication-date"></label><input
                        id="text-field-filter-publication-date" name="birthDate" type="text"
                        class="datepicker"/>

                </div>
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
                        <li class="collection-item" id=${fn:escapeXml(widget.id)}>
                            <div class="row">
                                <div class="col m3 s3">
                                    <i class="fas fa-cloud fa-7x"></i>
                                </div>
                                <div class="col m4 s4">
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
                                </div>
                                <div class="col m2 offset-m3" style="margin-top: 30px">
                                    <button id="add" class="btn waves-effect waves-light" type="submit" name="search">
                                        <fmt:message key="i18n.add"/>
                                    </button>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
    <script>

        let rating = localStorage.getItem("rating");

        $( document ).ready(function () {
            $("#weather-panel").on("click", function () {
                PostRequest.getInstance().make("marketplace/detailView/1", {
                });
            });
            $("#add").on("click", function () {

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

        });

        function onSearch() {

            let searchField = document.getElementById("text-field-search");
            console.log(document.getElementById("text-field-search"));
            let instance = M.Datepicker.getInstance(document.getElementById("text-field-filter-publication-date"));


            let date = instance.toString();

            if (date === "") {
                date = "2019-01-01";
            }

            localStorage.setItem("date",date);
            localStorage.setItem("rating",rating);

            let url = "${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search?" + $.param({
                searchQuery: searchField.value,
                date,
                rating
            });

            window.location.replace(url);
        }
    </script>
</html>
