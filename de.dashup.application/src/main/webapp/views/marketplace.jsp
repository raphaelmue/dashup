<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <a href="#" class="breadcrumb"><fmt:message key="i18n.marketplace"/></a>
                </div>
            </div>
        </nav>
            <div class="row" id="search-bar" style="margin-top: 25px">
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
                    <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" onclick="onSearch()">
                        <fmt:message key="i18n.go"/>
                    </button>
                </div>
            </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.featured"/></h3>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <div class="carousel carousel-slider center">
                    <c:forEach items="${featuredWidgets}" var="mapEntry">
                        <a style="display:block" href="${pageContext.request.contextPath}/marketplace/detailView/${fn:escapeXml(mapEntry.key.id)}">
                            <div class="carousel-item" href="#one!" style="background-color: var(--color-secondary);color: #ffffff">
                                <div class="row" style="margin: 10px">
                                    <div class="col">
                                        <i class="fas fa-${fn:escapeXml(mapEntry.key.iconCode)} fa-7x"></i>
                                    </div>
                                    <div class="col">
                                        <h1 style="margin-top: 20px">${fn:escapeXml(mapEntry.key.name)}</h1>
                                    </div>
                                </div>
                                <div class="row" style="margin-left: 10px; margin-right: 10px;">
                                    <p>
                                        ${fn:escapeXml(mapEntry.key.shortDescription)}
                                    </p>
                                </div>
                                <div class="row" style="margin: 0 auto; width: 164px">
                                    <div class="col">
                                        <div class="star-rating" style="font-size: 25px; margin-top: 15px; color: var(--color-dark-gray);">
                                            <div class="back-stars">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <div class="front-stars"
                                                     style="width:  ${fn:escapeXml(mapEntry.key.averageRating)}%;color: var(--color-background);">
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
                                    <div class="col" style="text-align: center; width: 100%;">
                                        <p>"${fn:escapeXml(mapEntry.value.text)}"</p>
                                        <p>By ${fn:escapeXml(mapEntry.value.userName)} ${fn:escapeXml(mapEntry.value.userSurname)}</p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.mostPopular"/></h3>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col s12 m6 offset-m3">
                <div class="row">
                    <c:forEach items="${mostDownloaded}" var="panel">
                        <div class="col m6">
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
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.bestRating"/></h3>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col s12 m6 offset-m3">
                <div class="row">
                    <c:forEach items="${bestRated}" var="panel">
                        <div class="col m6">
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
                                <h6><fmt:message key="i18n.rating" /></h6>
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
                                <h6><fmt:message key="i18n.publicationDate" /></h6>
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

        let rating = 0;

        $( document ).ready(function () {

            $('.chips').chips();
            $('.chips-placeholder').chips({
                secondaryPlaceholder: '+' + '<fmt:message key="i18n.tag" />'
            });

            $('#chips-categories').chips({
                placeholder: '<fmt:message key="i18n.selectCategory" />',
                secondaryPlaceholder: '+' + '<fmt:message key="i18n.category" />'
            });

            $("#nav-item-marketplace").parent().addClass("active");
            $('.carousel.carousel-slider').carousel({
                fullWidth: true,
                indicators: true
            });
        });

        $("#text-field-filter-publication-date").datepicker({
            format: "yyyy-mm-dd",
            yearRange: [2019, 2019],
            container: document.getElementById("search-bar")
        });
        $(".star").on("click", function (element) {
            let starId = element.target.id;
            let factor = starId.substr(1, 1);
            rating = factor * 20;
            let frontStars = document.getElementById("front-stars");
            frontStars.style.width = rating + "%";
        });

        function onSearch() {
            let searchField = document.getElementById("text-field-search");
            let instance = M.Datepicker.getInstance(document.getElementById("text-field-filter-publication-date"));
            let date = instance.toString();
            if(date === ""){
                date = "2019-01-01";
            }

            let tags = M.Chips.getInstance($('#chips-tags')).chipsData;
            let categoies = M.Chips.getInstance($('#chips-categories')).chipsData;
            localStorage.setItem("date",instance.toString());
            localStorage.setItem("rating",rating);
            let url = "${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search?" + $.param({searchQuery:searchField.value,date,rating});
            tags.forEach(tag => url+="&tags=" + tag.tag);
            categoies.forEach(category => url+="&categories=" + category.tag);
            window.location.replace(url);
        }

    </script>

    <jsp:include page="includes/webComponents.jsp" />
</html>