<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<head>
    <jsp:include page="./includes/headInclude.jsp" />
    <jsp:include page="./includes/styles.jsp" />
    <jsp:include page="./includes/scripts.jsp" />
    <title>dashup - <fmt:message key="i18n.layout"/></title>
</head>
<body>
<jsp:include page="includes/mainHeader.jsp"/>
    <main>
        <div class="wrapper">
            <form action="${pageContext.request.contextPath}/entry/handleLayout" method="POST">
                <h4><fmt:message key="i18n.background"/></h4>
                <div class="form-group">
                    <label for="backgroundColor"><fmt:message key="i18n.backgroundColor"/></label>
                    <div id="backgroundColor" name="background_color" class="bfh-colorpicker" data-name="colorpicker2" data-color="#000000"></div>
                </div>
                <div class="form-group">
                    <label for="backgroundImage"><fmt:message key="i18n.backgroundImage"/></label>
                    <div id="backgroundImage"class="custom-file">
                        <input name="background_image" type="file" class="custom-file-input" id="customFile">
                        <label class="custom-file-label" for="customFile"><fmt:message key="i18n.chooseFile"/></label>
                    </div>
                </div>

                <h4><fmt:message key="i18n.sections"/></h4>
                <div class="form-group">
                    <label for="headingSize"><fmt:message key="i18n.headingSize"/></label>
                    <input id="headingSize" name="heading_size" type="text" class="form-control bfh-number">
                </div>
                <div class="form-group">
                    <label for="headingColor"><fmt:message key="i18n.headingColor"/></label>
                    <div id="headingColor" name="heading_color" class="bfh-colorpicker" data-name="colorpicker2" data-color="#ffffff"></div>
                </div>

                <h4><fmt:message key="i18n.font"/></h4>
                <div class="form-group">
                    <label for="font"><fmt:message key="i18n.font"/></label>
                    <div id="font" class="bfh-selectbox bfh-fonts" data-family="Helvetica">
                        <input type="hidden" value="">
                        <a class="bfh-selectbox-toggle" role="button" data-toggle="bfh-selectbox" href="#">
                            <span class="bfh-selectbox-option input-large" data-option=""></span>
                            <b class="caret"></b>
                        </a>
                        <div class="bfh-selectbox-options">
                            <input name="font" type="text" class="bfh-selectbox-filter">
                            <div role="listbox">
                                <ul role="option">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </main>
    <jsp:include page="./includes/bodyInclude.jsp" />
</body>
</html>