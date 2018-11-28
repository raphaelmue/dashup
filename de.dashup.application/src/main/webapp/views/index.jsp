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
    </head>
    <body>
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
                <h1><fmt:message key="i18n.welcome"/>, ${name}!</h1>
            </div>

            <div>
                ${content}
            </div>
        </main>
        <jsp:include page="./includes/bodyInclude.jsp" />
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
</html>