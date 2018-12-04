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
            <form>
                <h4><fmt:message key="i18n.background"/></h4>
                <div class="form-group">
                    <label for="backgroundColor"><fmt:message key="i18n.backgroundColor"/></label>
                    <div id="backgroundColor" class="bfh-colorpicker" data-name="backgroundColor" data-color="#000000"></div>
                </div>
                <div class="form-group">
                    <label for="backgroundImage"><fmt:message key="i18n.backgroundImage"/></label>
                    <div id="backgroundImage"class="custom-file">
                        <input id="customFile" name="backgroundImage" type="file" class="custom-file-input">
                        <label class="custom-file-label" for="customFile"><fmt:message key="i18n.chooseFile"/></label>
                    </div>
                </div>

                <h4><fmt:message key="i18n.sections"/></h4>
                <div class="form-group">
                    <label for="headingSize"><fmt:message key="i18n.headingSize"/></label>
                    <input id="headingSize" name="headingSize" type="text" class="form-control bfh-number">
                </div>
                <div class="form-group">
                    <label for="headingColor"><fmt:message key="i18n.headingColor"/></label>
                    <div id="headingColor" class="bfh-colorpicker" data-name="headingColor" data-color="#ffffff"></div>
                </div>

                <h4><fmt:message key="i18n.font"/></h4>
                <div class="form-group">
                    <label for="font"><fmt:message key="i18n.font"/></label>
                    <%--<select id="font" class="form-control bfh-fonts" data-name="font" data-available="Arial,Calibri,Helvetica"></select>--%>
                    <div id="font" class="bfh-selectbox bfh-fonts" data-font="Arial" data-name="font"></div>
                </div>
                <button id="submit" type="button" class="btn btn-primary">Submit</button>
                <br />
                <div id="result"></div>
            </form>
        </div>
    </main>
    <jsp:include page="./includes/bodyInclude.jsp" />

    <script>
        $(document).ready(function() {
            $("#submit").on("click", function () {

                let formData = $("form").serializeArray();
                let requestBody = {};
                for(let i = 0; i < formData.length; i++){
                    requestBody[formData[i].name] = formData[i].value;
                }
                requestBody["backgroundImage"] = "notApplicable";

                $.ajax({
                    type: "POST",
                    url: "../rest/handleLayout",
                    data: JSON.stringify(requestBody),
                    dataType: "json",
                    contentType : "application/json"
                }).done(function() {
                        $("#result").append("" +
                            "<div id=\"message\" class=\"alert alert-success\">\n" +
                            "  <strong>Success!</strong> Layout saved.\n" +
                            "</div>");

                        setTimeout(() => {
                            $("#message").remove();
                        }, 3000);
                    })
                    .fail(function() {
                        $("#result").append("" +
                        "<div id=\"message\" class=\"alert alert-danger\">\n" +
                        "   <strong>Failure!</strong> Error in field values.\n" +
                        "</div>");

                        setTimeout(() => {
                            $("#message").remove();
                        }, 3000);
                    });
            });
        });
    </script>
</body>
</html>