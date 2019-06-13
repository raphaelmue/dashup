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
                                        <div class="star-ratingValue" style="font-size: 25px; margin-top: 15px; color: var(--color-dark-gray);">
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
                                        <p>By ${fn:escapeXml(mapEntry.value.userName)}</p>
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

        <jsp:include page="includes/filterModal.jsp">
            <jsp:param name="lang" value="${param.lang}"/>
            <jsp:param name="categories" value="${categories}"/>
            <jsp:param name="tags" value="${tags}"/>
            <jsp:param name="tags" value="${publisher}"/>
        </jsp:include>

    </body>
    <script type="text/javascript">
        $( document ).ready(function () {
            $("#nav-item-marketplace").parent().addClass("active");
            $('.carousel.carousel-slider').carousel({
                fullWidth: true,
                indicators: true
            });
        });
    </script>
</html>