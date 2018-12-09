<%--
  Created by IntelliJ IDEA.
  User: D070413
  Date: 20.11.2018
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="i18n" />
<html>




<head>
    <title>dashup - <fmt:message key="i18n.layoutmode"/></title>


    <jsp:include page="./includes/headInclude.jsp" />
    <jsp:include page="./includes/styles.jsp" />
    <jsp:include page="./includes/scripts.jsp" />
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">-->
    <link rel="stylesheet" href="./../styles/layoutmode.css">
    <jsp:include page="./includes/webComponents.jsp" />
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">-->

</head>
<body class="text-white" onload="load()">

<header>
    <div class="wrapper">
        <h1 class="heading">dashup</h1>
        <nav id="navbar">
            <ul>
                <li id="nav-item-marketplace">
                    <a class="nav-item nav-item-hover" href="${pageContext.request.contextPath}/marketplace">
                        <fmt:message key="i18n.marketplace" />
                    </a>
                </li>
                <li id="nav-item-layout">
                    <a class="nav-item nav-item-hover" href="${pageContext.request.contextPath}/layout">
                        <fmt:message key="i18n.layout" />
                    </a>
                </li>
                <li id="nav-item-profile">
                    <a class="nav-item" href="${pageContext.request.contextPath}/profile">
                        ${name}
                    </a>
                </li>
            </ul>
        </nav>
        <div class="clear-float"></div>
    </div>
</header>


<main>
    <div class="wrapper">
        <h1><fmt:message key="i18n.layout"/>, ${name}!</h1>
        <div>
            ${content}
        </div>
    </div>
</main>

<div id="bottomRight">
    <ul><button type="button" class="btn btn-circle btn-lg">undo</button></ul>
    <ul><button type="button" onclick="addSection()" class="btn btn-circle btn-lg">add</button></ul>
    <ul><button type="button" onclick="onSubmit()" class="btn btn-circle btn-lg">submit</button></ul>
</div>


<jsp:include page="./includes/bodyInclude.jsp" />
<script  src="./../scripts/dragdrop.js"></script>
<script>
    $( document ).ready(function() {
        $("#navbar").navigationBar({
            structure: {
                profile: {
                    groups: {
                        settings: {
                            name: "<fmt:message key="i18n.settings"/>",
                            elements: {
                                settings: {
                                    name: "<fmt:message key="i18n.settings"/>",
                                    url: "",
                                    iconClass: "fa fa-cog"
                                },
                                layout: {
                                    name: "<fmt:message key="i18n.layout"/>",
                                    url: "",
                                    iconClass: "fa fa-pencil-alt"
                                }
                            }
                        },
                        profile: {
                            name: "<fmt:message key="i18n.profile" />",
                            elements: {
                                myProfile: {
                                    name: "<fmt:message key="i18n.myProfile" />",
                                    url: "",
                                    iconClass: "fa fa-user"
                                },
                                logout: {
                                    name: "<fmt:message key="i18n.logout" />",
                                    url: "/handleLogout",
                                    iconClass: "fa fa-sign-out-alt"
                                }
                            }
                        }
                    }
                }
            },
            align: "center",
        });
    });
</script>


</body>

</body>
</html>
