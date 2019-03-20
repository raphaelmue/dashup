<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
    <jsp:include page="includes/head.jsp"/>
    <body>
        <jsp:include page="includes/header.jsp"/>
        <div class="container">
            <div>
                ${content}
            </div>
        </div>

        <div id="bottom-right">
            <ul>
                <button type="button" onclick="onUndo()" class="btn btn-primary">Undo</button>
            </ul>
            <ul>
                <button type="button" id="add_section_btn" onclick="addSection()" class="btn btn-primary">Add</button>
            </ul>
            <ul>
                <button type="button" onclick="onSubmit()" class="btn btn-primary">Submit</button>
            </ul>
        </div>
    <script src="./../libraries/dragdrop.js"></script>
    </body>
</html>
