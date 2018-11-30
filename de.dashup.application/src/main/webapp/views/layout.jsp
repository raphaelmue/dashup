<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="i18n"/>
<html>
<head>
    <jsp:include page="./includes/headInclude.jsp" />
    <jsp:include page="./includes/styles.jsp" />
    <jsp:include page="./includes/scripts.jsp" />

    <link rel="stylesheet" media="screen" type="text/css" href="../scripts/colorpicker/css/colorpicker.css" />

    <link rel="stylesheet" href="../scripts/fontpicker/styles/fontselect-default.css">

    <title>dashup - <fmt:message key="i18n.layout"/></title>
</head>
<body>
<jsp:include page="includes/mainHeader.jsp"/>
    <main>
        <div class="wrapper">
            <h1><fmt:message key="i18n.layout"/></h1>
            <div class="container">
                <div class="row">
                    <div class="col-sm">
                        <h4><fmt:message key="i18n.background"/></h4>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.backgroundColor"/></th>
                                <td>
                                    <a id="colorSelector" href="#"><fmt:message key="i18n.chooseColor"/></a>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.backgroundImage"/></th>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>

                        <h4><fmt:message key="i18n.sections"/></h4>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.headingSize"/></th>
                                <td></td>
                            </tr>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.headingColor"/></th>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>

                        <h4><fmt:message key="i18n.font"/></h4>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th scope="row"><fmt:message key="i18n.font"/></th>
                                <td><input id="font" type="text"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="./includes/bodyInclude.jsp" />
    <script type="text/javascript" src="../scripts/colorpicker/js/colorpicker.js"></script>
    <script>
        $('#colorSelector').ColorPicker({
            color: '#0000ff',
            onShow: function (colpkr) {
                $(colpkr).fadeIn(500);
                return false;
            },
            onHide: function (colpkr) {
                $(colpkr).fadeOut(500);
                return false;
            },
            onChange: function (hsb, hex, rgb) {
                $('#body').css('backgroundColor', '#' + hex);
            }
        });
    </script>

    <script src="../scripts/fontpicker/jquery.fontselect.js"></script>
    <script>
        $(function(){
            $('#font').fontselect().change(function(){
                var font = $(this).val().replace(/\+/g, ' ');
                font = font.split(':');
                $('*').css('font-family', font[0] + " !important");
            });
        });
    </script>

</body>
</html>