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
            <div class="col m6 offset-m3 s10 offset-s1">
                <div class="input-field">
                    <input id="text-field-login-email" name="email" type="text" class="validate">
                    <label for="text-field-login-email"><fmt:message key="i18n.enterSearchterm" /></label>
                </div>
             </div>
        </div>

    </body>
    <script>
        $( document ).ready(function () {
            $("#nav-item-marketplace").parent().addClass("active");
        });
    </script>
</html>