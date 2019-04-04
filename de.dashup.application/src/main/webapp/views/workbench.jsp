<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<!doctype html>
<html lang="en">
    <jsp:include page="includes/head.jsp" />
    <body>
        <jsp:include page="includes/header.jsp"/>
        <aside>
            <h4><fmt:message key="i18n.workbench" /></h4>
            <ul>
                <c:forEach items="${drafts}" var="draft">
                    <li>
                        <a href="#" class="waves-effect">${draft}</a>
                    </li>
                </c:forEach>
            </ul>
            <a href="#" class="btn-floating btn-large waves-effect waves-light"><i class="fas fa-plus"></i></a>
        </aside>
        <main>
            <c:choose>
                <c:when test="${currentDraft != null}">
                    <ul class="tabs">
                        <li class="tab"><a class="active" href="#code"><fmt:message key="i18n.code" /></a></li>
                        <li class="tab"><a href="#basicInformation"><fmt:message key="i18n.basicInformation" /></a></li>
                    </ul>
                    <div id="code" class="col s12">
                        <div class="container">
                            <div class="row">
                                <div class="col s12 m6">
                                    <h3 style="margin-bottom: 50px;"><fmt:message key="i18n.code" /></h3>
                                    <div class="input-field">
                                        <textarea id="textarea-code" class="materialize-textarea"></textarea>
                                        <label for="textarea-code"><fmt:message key="i18n.code" /></label>
                                    </div>
                                </div>
                                <div class="col s12 m6">
                                    <h3 style="margin-bottom: 50px;"><fmt:message key="i18n.preview" /></h3>
                                    <div class="col card s12 m12" id="pre-view-container" ></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="basicInformation" class="col s12">
                        <div class="container">
                            <h3><fmt:message key="i18n.basicInformation" /></h3>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; margin: 30% auto">
                        <h5 style="text-align: center;">
                            <i style="font-size: 50px; margin-bottom: 20px;" class="fas fa-drafting-compass"></i>
                            <br />
                            <fmt:message key="i18n.selectDraft" />
                        </h5>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

    </body>
    <script type="text/javascript">
        $( document ).ready(function () {
            $("#nav-item-workbench").parent().addClass("active");

            $(".tabs").tabs({
                swipeable: true
            });

            $("#textarea-code").bind('input propertychange', function() {
                console.log("changed");
                console.log($("#textarea-code").val());

                $("#pre-view-container").html($("#textarea-code").val());
            });
        });
    </script>
</html>