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
        <div class="col m2">
            <i class="fas fa-cloud"></i>
        </div>
        <div class="col m8">
            <div class="row">
                <div class="col m2">
                    <h2>Weather Panel</h2>
                </div>
                <div class="col m2 offset-m4">
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
        </div>
    </div>
    <div class="row">
        And here is the rating.
    </div>
    <div class="row">
        <ul id="tabs-swipe-demo" class="tabs">
            <li class="tab col s3"><a href="#test-swipe-1">Test 1</a></li>
            <li class="tab col s3"><a class="active" href="#test-swipe-2">Test 2</a></li>
            <li class="tab col s3"><a href="#test-swipe-3">Test 3</a></li>
        </ul>
        <div id="test-swipe-1" class="col s12 blue">Test 1</div>
        <div id="test-swipe-2" class="col s12 red">Test 2</div>
        <div id="test-swipe-3" class="col s12 green">Test 3</div>
    </div>
    </body>
    <script>
        $( document ).ready(function () {
            $('.tabs').tabs();
        });

    </script>
</html>
