<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<header class="develop-toolbar mdc-toolbar mdc-toolbar--platform">
    <nav>
        <div class="nav-wrapper">
            <a href="#" class="brand-logo" style="font-size: 20px;">D A S H U P</a>
            <a href="#" data-target="mobile-nav-bar" class="sidenav-trigger"><i class="fas fa-bars"></i></a>
            <ul class="right hide-on-med-and-down">
                <li><a href="${pageContext.request.contextPath}/" id="nav-item-dashboard"><fmt:message key="i18n.dashboard"/></a></li>
                <li><a href="${pageContext.request.contextPath}/marketplace/" id="nav-item-marketplace"><fmt:message key="i18n.marketplace"/></a></li>
                <li><a href="${pageContext.request.contextPath}/layout/" id="nav-item-layout"><fmt:message key="i18n.layout"/></a></li>
                <li><a href="${pageContext.request.contextPath}/settings/" id="nav-item-settings"><fmt:message key="i18n.settings"/></a></li>
            </ul>
        </div>
    </nav>

    <ul class="sidenav" id="mobile-nav-bar">
        <li><a href="${pageContext.request.contextPath}/" ><fmt:message key="i18n.dashboard"/></a></li>
        <li><a href="${pageContext.request.contextPath}/marketplace/"><fmt:message key="i18n.marketplace"/></a></li>
        <li><a href="${pageContext.request.contextPath}/layout/"><fmt:message key="i18n.workbench"/></a></li>
        <li><a href="${pageContext.request.contextPath}/settings/"><fmt:message key="i18n.settings"/></a></li>
    </ul>
</header>
