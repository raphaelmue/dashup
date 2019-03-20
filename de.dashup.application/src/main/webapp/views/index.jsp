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
            ${content}
        </div>

        <a href="${pageContext.request.contextPath}/layoutMode/" class="btn-floating btn-large waves-effect waves-light"><i class="fas fa-edit"></i></a>

        <script>
            $( document ).ready(function () {
                $("#nav-item-dashboard").parent().addClass("active");
            });
        </script>
    </body>
</html>