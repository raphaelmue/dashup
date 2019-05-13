<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp"/>
        <div class="container">
            <div class="row">
                <h3><fmt:message key="i18n.marketplace" /></h3>
            </div>
        </div>

    </body>
    <script type="text/javascript">
        $( document ).ready(function () {
            $("#nav-item-marketplace").parent().addClass("active");
        });
    </script>
</html>