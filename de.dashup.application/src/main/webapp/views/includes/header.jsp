<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<header class="develop-toolbar mdc-toolbar mdc-toolbar--platform">
    <nav>
        <div class="nav-wrapper">
            <a href="#" class="brand-logo" style="font-size: 20px;">D A S H U P</a>
            <ul id="nav-mobile" class="right hide-on-med-and-down">
                <li><a href="#" class="develop-tab develop-tab--active"><fmt:message key="i18n.dashboard"/></a></li>
                <li><a href="#" class="develop-tab"><fmt:message key="i18n.marketplace"/></a></li>
                <li><a href="#" class="develop-tab"><fmt:message key="i18n.layout"/></a></li>
                <li><a href="#" class="develop-tab"><fmt:message key="i18n.settings"/></a></li>
            </ul>
        </div>
    </nav>
</header>
