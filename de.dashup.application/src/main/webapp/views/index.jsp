<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <head>
        <title>dashup</title>

        <jsp:include page="./includes/headInclude.jsp" />
        <jsp:include page="./includes/styles.jsp" />
        <jsp:include page="./includes/scripts.jsp" />

        <style>
            * {
                font-family: ${font_text}, sans-serif
            }
            body {
                background: url(${background_image}) no-repeat;
                background-color: ${background_color};
                background-size: cover;
                min-height: 100vh;
            }
            .sectionHeading {
                font-size: ${heading_size}pt;
                font-family: ${font_heading};
                color: ${heading_color};
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/mainHeader.jsp"/>
        <main>
            <div class="wrapper">
                <h1 class="sectionHeading"><fmt:message key="i18n.welcome"/>, ${name}!</h1>
            </div>
        </main>
        <jsp:include page="./includes/bodyInclude.jsp" />
    </body>
</html>