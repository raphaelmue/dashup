<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp" />
    <body>
    <jsp:include page="includes/header.jsp" />
        <jsp:include page="includes/header.jsp"/>
        <main>
            <div class="wrapper">

                <div>
                    ${content}
                </div>
            </div>
        </main>

        <script>
            $( document ).ready(function () {
                $("#nav-item-home").children().addClass("selected");
            });
        </script>
    </body>
</html>