<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- jQuery API -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>

    <!-- Material Design API -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

    <!-- Fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat|Raleway|Roboto">
    <link rel="stylesheet" type="text/css" href="../../typo/fontawesome-free-5.0.1/web-fonts-with-css/css/fontawesome-all.min.css" />

    <!-- Style -->
    <link rel="stylesheet" href="../../styles/theme.style.css"/>
    <link rel="stylesheet" href="../../styles/main.style.css"/>

    <!-- JavaScript Classes -->
    <script type="text/javascript" src="../../libraries/classes/PostRequest.js"></script>
    <script type="text/javascript" src="../../libraries/index.js"></script>

    <title>dashup</title>
</head>