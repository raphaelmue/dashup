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
        <form action="${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search" method="post">
            <div class="row" style="margin-top: 25px">
                <div class="col m4 offset-m4 s8 offset-s1">
                    <div class="input-field">
                        <input id="text-field-search-term" name="email" type="text" class="validate">
                        <label for="text-field-search-term"><fmt:message key="i18n.enterSearchterm"/></label>
                    </div>
                </div>
                <div class="col m2 s2" style="margin-top: 18px">
                    <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" type="submit"
                            name="search">
                        <fmt:message key="i18n.go"/>
                    </button>
                </div>
            </div>
        </form>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.featured"/></h3>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <div class="carousel carousel-slider center">
                    <div class="carousel-item" href="#one!" style="background-color: var(--color-secondary);color: var(--color-black)">
                        <div class="row" style="margin: 10px">
                            <div class="col">
                                <i class="fas fa-${fn:escapeXml(carouselWidget.iconCode)} fa-7x"></i>
                            </div>
                            <div class="col">
                                <h1 style="margin-top: 25px">${fn:escapeXml(carouselWidget.name)}</h1>
                            </div>
                        </div>
                        <div class="row" style="margin-left: 10px; margin-right: 10px;">
                            <p>
                                ${fn:escapeXml(carouselWidget.shortDescription)}
                            </p>
                        </div>
                        <div class="row" style="margin-right: 10px; margin-left: 10px;">
                            <div class="col">
                                <div class="star-rating" style="font-size: 25px; margin-top: 15px">
                                    <div class="back-stars">
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <div class="front-stars"
                                             style="width:  ${fn:escapeXml(carouselWidget.averageRating)}%">
                                            <i class="fa fa-star" aria-hidden="true"></i>
                                            <i class="fa fa-star" aria-hidden="true"></i>
                                            <i class="fa fa-star" aria-hidden="true"></i>
                                            <i class="fa fa-star" aria-hidden="true"></i>
                                            <i class="fa fa-star" aria-hidden="true"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col m9" style="margin-top: 0px">
                                <p>"This panel is so fantastic. Really the best one I have ever seen! You have to download it now."</p>
                                <p>By Der Coolste</p>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item grey lighten-2 black-text" href="#two!">
                        <h2>Second Panel</h2>
                        <p class="white-text">This is your second panel</p>
                    </div>
                    <div class="carousel-item grey lighten-2 black-text" href="#three!">
                        <h2>Third Panel</h2>
                        <p class="white-text">This is your third panel</p>
                    </div>
                    <div class="carousel-item grey lighten-2 black-text" href="#four!">
                        <h2>Fourth Panel</h2>
                        <p class="white-text">This is your fourth panel</p>
                    </div>
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

    <jsp:include page="includes/webComponents.jsp" />
</html>