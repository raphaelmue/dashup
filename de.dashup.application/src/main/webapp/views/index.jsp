<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <head>
        <title>dashup</title>

        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/webComponents.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />
    </head>
    <body>
        <jsp:include page="includes/mainHeader.jsp"/>
        <main>
            <div class="wrapper">
                <h1><fmt:message key="i18n.welcome"/>, ${name}!</h1>
                <div>
                    ${content}
                </div>
            </div>
        </main>
        <jsp:include page="./includes/bodyInclude.jsp" />

        <script>
            $( document ).ready(function () {
                $("#nav-item-home").children().addClass("selected");
            });
        </script>
    </body>
</html>