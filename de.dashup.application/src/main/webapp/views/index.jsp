<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp" />
    <style>
        body {
            background: url('${fn:escapeXml(backgroundImage)}') no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
    </style>
    <body>
        <jsp:include page="includes/header.jsp"/>
        <div class="container">
            ${content}
        </div>

        <a href="${pageContext.request.contextPath}/layoutMode/" id="enter-layout-mode" class="btn-floating btn-large waves-effect waves-light"><i class="fas fa-edit"></i></a>

        <script type="text/javascript">
            $( document ).ready(function () {
                $("#nav-item-dashboard").parent().addClass("active");
            });
        </script>

        <jsp:include page="includes/webComponents.jsp" />
    </body>
</html>