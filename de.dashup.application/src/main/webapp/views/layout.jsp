<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
    <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp"/>
        <div class="container">
            <form>
            <h4><fmt:message key="i18n.background"/></h4>
            <div class="form-group">
                <label for="backgroundColor"><fmt:message key="i18n.backgroundColor"/></label>
                <div id="backgroundColor" class="bfh-colorpicker" data-name="backgroundColor" data-color="${background_color}"></div>
            </div>
            <div class="form-group">
                <label for="backgroundImage"><fmt:message key="i18n.backgroundImage"/></label>
                <input id="backgroundImage" name="backgroundImage" type="text" class="form-control" placeholder="Enter URL" value="${background_image}">
            </div>

            <h4><fmt:message key="i18n.sections"/></h4>
            <div class="form-group">
                <label for="headingColor"><fmt:message key="i18n.headingColor"/></label>
                <div id="headingColor" class="bfh-colorpicker" data-name="headingColor" data-color="${heading_color}"></div>
            </div>
            <div class="form-group">
                <label for="headingSize"><fmt:message key="i18n.headingSize"/></label>
                <input id="headingSize" name="headingSize" type="text" class="form-control bfh-number" value="${heading_size}" data-min="12" data-max="40">
                <small id="restrictions" class="form-text text-muted">Choose a value between 12pt and 40pt.</small>
            </div>

            <h4><fmt:message key="i18n.font"/></h4>
            <div class="form-group">
                <label for="fontHeading"><fmt:message key="i18n.fontHeading"/></label>
                <select id="fontHeading" name="fontHeading" class="form-control bfh-fonts" data-font="${font_heading}" data-available="Andale Mono,Arial,Arial Black,Avant Garde,Calibri,Courier New,Helvetica,Impact,Times New Roman,Verdana" data-blank="false" ></select>
            </div>
            <div class="form-group">
                <label for="fontText"><fmt:message key="i18n.fontText"/></label>
                <select id="fontText" name="fontText" class="form-control bfh-fonts" data-font="${font_text}" data-available="Andale Mono,Arial,Arial Black,Avant Garde,Calibri,Courier New,Helvetica,Impact,Times New Roman,Verdana" data-blank="false" ></select>
            </div>
            <button id="submit" type="button" class="btn btn-primary">Submit</button>
            <br />
            <br />
            <div id="result"></div>
        </form>
        </div>

        <script>
            $(document).ready(function() {
                $("#submit").on("click", function () {
                    let formData = $("form").serializeArray();
                    let requestBody = {};
                    let success = function(){
                        $("#result").append("" +
                            "<div id=\"message\" class=\"alert alert-success\">\n" +
                            "  <strong>Success!</strong> Layout saved.\n" +
                            "</div>");

                        setTimeout(() => {
                            $("#message").remove();
                        }, 3000);
                    };
                    let failure = function(){
                        $("#result").append("" +
                            "<div id=\"message\" class=\"alert alert-danger\">\n" +
                            "   <strong>Failure!</strong> Error in field values.\n" +
                            "</div>");

                        setTimeout(() => {
                            $("#message").remove();
                        }, 3000);
                    };
                    let post = function(){
                        for(let i = 0; i < formData.length; i++){
                            requestBody[formData[i].name] = formData[i].value;
                        }

                        $.ajax({
                            type: "POST",
                            url: "../rest/handleLayout",
                            data: JSON.stringify(requestBody),
                            dataType: "json",
                            contentType : "application/json"
                        }).done(function() {
                            success();
                        }).fail(function() {
                            failure();
                        });
                    };

                    if(formData.length <= 4){
                        failure();
                    } else if(formData.length == 5){
                        let imageMissing = true;
                        for(let i = 0; i < formData.length; i++){
                            if(formData[i].name == "backgroundImage"){
                                imageMissing = false;
                                break;
                            }
                        }
                        if(!imageMissing){
                            failure();
                        } else {
                            post();
                        }
                    } else {
                        post();
                    }
                });
            });
        </script>
    </body>
</html>