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
                    <a href="#" class="breadcrumb"><fmt:message key="i18n.marketplace" /></a>
                </div>
            </div>
        </nav>
        <%--<form action="${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search" method="POST">--%>
        <%--<form onsubmit="onSearch()">--%>
            <div id="begin" class="row" style="margin-top: 25px">
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
                        <fmt:message key="i18n.go" />
                    </button>

                </div>
            </div>
        <%--</form>--%>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.featured" /></h3>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <div class="carousel carousel-slider center">
                    <div class="carousel-item red white-text" href="#one!">
                        <h2>First Panel</h2>
                        <p class="white-text">This is your first panel</p>
                    </div>
                    <div class="carousel-item amber white-text" href="#two!">
                        <h2>Second Panel</h2>
                        <p class="white-text">This is your second panel</p>
                    </div>
                    <div class="carousel-item green white-text" href="#three!">
                        <h2>Third Panel</h2>
                        <p class="white-text">This is your third panel</p>
                    </div>
                    <div class="carousel-item blue white-text" href="#four!">
                        <h2>Fourth Panel</h2>
                        <p class="white-text">This is your fourth panel</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.mostPopular" /></h3>
                <hr/>
            </div>
        </div>
        <div class="row">
            <div class="col s12 m3 offset-m3">
                <div class="card horizontal">
                    <div class="card-image">
                        <img src="https://lorempixel.com/100/190/nature/6">
                    </div>
                    <div class="card-stacked">
                        <div class="card-content">
                            <p>I am a very simple card. I am good at containing small bits of information.</p>
                        </div>
                        <div class="card-action">
                            <a href="#">This is a link</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col s12 m3">
                <div class="card horizontal">
                    <div class="card-image">
                        <img src="https://lorempixel.com/100/190/nature/6">
                    </div>
                    <div class="card-stacked">
                        <div class="card-content">
                            <p>I am a very simple card. I am good at containing small bits of information.</p>
                        </div>
                        <div class="card-action">
                            <a href="#">This is a link</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <h3><fmt:message key="i18n.bestRating" /></h3>
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
                                           <i class="fas fa-cloud fa-3x"></i>
                                           <h5 style="display: inline">${fn:escapeXml(panel.name)}</h5>
                                       </div>
                                        ${fn:escapeXml(panel.shortDescription)}
                                    </div>
                                    <div class="card-action">
                                        <a href="/marketplace/detailView/${panel.id}"><fmt:message key="i18n.showMore" /></a>
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
                                <h6>Publication Date</h6>
                        </div>
                        <div class="col s8 m6">

                            <label for="text-field-filter-publication-date"></label><input
                                id="text-field-filter-publication-date" name="birthDate" type="text"
                                class="datepicker"
                                value="<fmt:message key="i18n.showMore" />"/>

                        </div>
                        </div>

                        <div class="row">
                            <div class="col s4 m6 valign-wrapper">
                                <h6>Rating</h6>
                        </div>
                        <div class="col s8 m6">
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
            $("#nav-item-marketplace").parent().addClass("active");
            $('.carousel.carousel-slider').carousel({
                fullWidth: true,
                indicators: true
            });

            document.addEventListener('DOMContentLoaded', function () {
                let elems = document.querySelectorAll('.modal');
                let instances = M.Modal.init(elems, options);
            });


            $("#text-field-filter-publication-date").datepicker({
                format: "yyyy-mm-dd",
                yearRange: [2019, 2019],
                container: document.getElementById("begin")
            });

            $(".star").on("click", function (element) {
                let starId = element.target.id;
                let factor = starId.substr(1, 1);
                rating = factor * 20;

                let frontStars = document.getElementById("front-stars");
                frontStars.style.width = rating + "%";
            });
        });

        // Or with jQuery

        $(document).ready(function () {
            $('.modal').modal();
        });

        function onSearch() {

            let searchField = document.getElementById("text-field-search");
            let instance = M.Datepicker.getInstance(document.getElementById("text-field-filter-publication-date"));

            let date = instance.toString();

            if(date === ""){
                date = "2019-01-01";
            }

            localStorage.setItem("date",instance);
            localStorage.setItem("rating",rating);

            let url = "${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search?" + $.param({searchQuery:searchField.value,date,rating});

            window.location.replace(url);
        }

    </script>

    <jsp:include page="includes/webComponents.jsp" />
</html>