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
        <div class="row" style="margin-top: 25px">
            <div class="col m4 offset-m4 s8 offset-s1">
                <div class="input-field">
                    <input id="text-field-login-email" name="email" type="text" class="validate">
                    <label for="text-field-login-email"><fmt:message key="i18n.enterSearchterm" /></label>
                </div>
            </div>
            <div class="col m2 s2" style="margin-top: 18px">
                <button id="btn_start_search_marketplace" class="btn waves-effect waves-light" type="submit" name="search">
                    <fmt:message key="i18n.go" />
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
                    <li class="collection-item avatar">
                        <img src="https://openclipart.org/image/2400px/svg_to_png/282766/cloud-with-flat-base-CC0-by-Erland.png" alt="" class="circle">
                        <span class="title">Weather Panel</span>
                        <p>Here is the short description <br>
                            And our rating
                        </p>
                        <a href="#!" class="secondary-content">Or our rating is here</a>
                    </li>
                    <li class="collection-item avatar">
                        <i class="material-icons circle">folder</i>
                        <span class="title">Title</span>
                        <p>First Line <br>
                            Second Line
                        </p>
                        <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
                    </li>
                    <li class="collection-item avatar">
                        <i class="material-icons circle green">insert_chart</i>
                        <span class="title">Title</span>
                        <p>First Line <br>
                            Second Line
                        </p>
                        <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
                    </li>
                    <li class="collection-item avatar">
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
</html>
