<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="row">
            <div class="col m2" style="margin-top: 2%; margin-left: 2%">
                <i class="fas fa-cloud fa-10x"></i>
            </div>
            <div class="col m8">
                <div class="row">
                    <div class="col m4">
                        <h2>Weather Panel</h2>
                    </div>
                    <div class="col m2 offset-m6" style="margin-top: 40px">
                        <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" type="submit" name="search">
                            <fmt:message key="i18n.add" />
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col m8">
                        Here is the short description.
                    </div>
                </div>
                <div class="row">
                    <div class="col m8">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                    </div>
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
                <div id="overview-tab" class="col s12">Overview</div>
                <div id="ratings-tab" class="col s12">Ratings</div>
                <div id="similar-tab" class="col s12">Similar</div>
            </div>
        </div>
    </body>
    <script>
        $( document ).ready(function () {
            $('.tabs').tabs();
        });

    </script>
</html>
