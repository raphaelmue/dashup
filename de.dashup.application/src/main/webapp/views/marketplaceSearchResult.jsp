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
                    <a href="#" class="breadcrumb"><fmt:message key="i18n.marketplace"/></a>
                </div>
            </div>
        </nav>
        <div class="row" style="margin-top: 25px">
            <div class="col m4 offset-m4 s8 offset-s1">
                <div class="input-field">
                    <input id="text-field-search-term" name="email" type="text" class="validate">
                    <label for="text-field-search-term"><fmt:message key="i18n.enterSearchterm"/></label>
                </div>
            </div>
            <div class="col m2 s2" style="margin-top: 18px">
                <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" type="submit" name="search">
                    <fmt:message key="i18n.go"/>
                </button>
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                Do some filter stuff here.
            </div>
        </div>
        <div class="row">
            <div class="col m6 offset-m3 s10 offset-s1">
                <hr/>
                <ul class="collection">
                    <li class="collection-item" id="weather-panel">
                        <div class="row">
                            <div class="col m3 s3">
                                <i class="fas fa-cloud fa-7x"></i>
                            </div>
                            <div class="col m4 s4">
                                <h5>Weather Panel</h5>
                                <div>This is the short description of this panel</div>
                                <div style="margin-top: 5px">
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                </div>
                            </div>
                            <div class="col m2 offset-m3" style="margin-top: 30px">
                                <button id="add" class="btn waves-effect waves-light" type="submit" name="search">
                                    <fmt:message key="i18n.add"/>
                                </button>
                            </div>
                        </div>
                    </li>
                    <li class="collection-item">
                        <i class="material-icons circle">folder</i>
                        <span class="title">Title</span>
                        <p>First Line <br>
                            Second Line
                        </p>
                        <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
                    </li>
                    <li class="collection-item">
                        <i class="material-icons circle green">insert_chart</i>
                        <span class="title">Title</span>
                        <p>First Line <br>
                            Second Line
                        </p>
                        <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
                    </li>
                    <li class="collection-item">
                        <i class="material-icons circle red">play_arrow</i>
                        <span class="title">Title</span>
                        <p>First Line <br>
                            Second Line
                        </p>
                        <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
                    </li>
                </ul>
            </div>
        </div>
    </body>
    <script>
        $( document ).ready(function () {
            $("#weather-panel").on("click", function () {
                PostRequest.getInstance().make("marketplace/detailView/1", {
                });
            });
            $("#add").on("click", function () {

            });
        });
    </script>
</html>
