<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="i18n" />
<html>
<body>
<!-- Modal Structure -->
<div id="widget-filter" class="modal">
    <div class="modal-content">
        <div class="row">
            <div class="col s12 m6 ">

                <div class="row">
                    <h5>Filter Options</h5>
                </div>

                <div class="row">

                    <div class="col s4 m6 valign-wrapper">
                        <h6>Publication Date</h6>
                    </div>
                    <div class="col s8 m6">

                        <label for="text-field-filter-publication-date"></label><input
                            id="text-field-filter-publication-date" name="birthDate" type="text"
                            class="datepicker"
                            value="<fmt:message key="i18n.showMore" />"/>

                    </div>
                </div>

                <div class="row">
                    <div class="col s4 m6 valign-wrapper">
                        <h6>Rating</h6>
                    </div>
                    <div class="col s8 m6">
                        <div class="star-rating"
                             style="font-size: 25px; margin-top: 15px; color: var(--color-dark-gray);">
                            <div class="back-stars star">
                                <i id="i1" class="fa fa-star" aria-hidden="true"></i>
                                <i id="i2" class="fa fa-star" aria-hidden="true"></i>
                                <i id="i3" class="fa fa-star" aria-hidden="true"></i>
                                <i id="i4" class="fa fa-star" aria-hidden="true"></i>
                                <i id="i5" class="fa fa-star" aria-hidden="true"></i>
                                <div id="front-stars" class="front-stars star"
                                     style="width:  20%; color: var(--color-primary);">
                                    <i id="f1" class="fa fa-star" aria-hidden="true"></i>
                                    <i id="f2" class="fa fa-star" aria-hidden="true"></i>
                                    <i id="f3" class="fa fa-star" aria-hidden="true"></i>
                                    <i id="f4" class="fa fa-star" aria-hidden="true"></i>
                                    <i id="f5" class="fa fa-star" aria-hidden="true"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#!" class="modal-close waves-effect waves-green btn-flat">OK</a>
        </div>
    </div>
</div>

</body>
</html>
