<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="i18n"/>
<!-- Modal Structure -->
<div id="widget-filter" class="modal">
    <div class="modal-content">
        <div class="row">
            <div class="col s12">

                <div class="row">
                    <h5>Filter Options</h5>
                </div>

                <div class="row valign-wrapper">
                    <div class="col s4 m3 l3 ">
                        <h6><fmt:message key="i18n.tags"/></h6>
                    </div>
                    <div class="col s8 m9 l9">
                        <div id="chips-tags" class="col s12 chips chips-placeholder input-field">
                            <input class="input" placeholder="<fmt:message key="i18n.enterTags"/>">
                        </div>
                    </div>
                </div>

                <div class="row valign-wrapper">
                    <div class="col s4 m3 l3">
                        <h6><fmt:message key="i18n.rating"/></h6>
                    </div>
                    <div class="col s8 m9 l9">
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

                <div class="row valign-wrapper">
                    <div class="col s4 m3 l3">
                        <h6><fmt:message key="i18n.category"/></h6>
                    </div>
                    <div class="col s8 m9 l9 valign-wrapper">
                        <div id="chips-categories" class="col s8 chips chips-placeholder input-field">
                            <input class="input" disabled placeholder=<fmt:message key="i18n.selectCategory"/>>
                        </div>
                        <div class="col s4 valign-wrapper">
                            <div class="input-field">
                                <label for="category-dropdown"></label><select name="size" id="category-dropdown">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.name}">
                                            <fmt:message key="i18n.${category.name}"/></option>
                                    </c:forEach>
                                </select>
                                <label><fmt:message key="i18n.category"/></label>
                            </div>
                            <a id="add-category-button" href="#" class="waves-effect waves-light">
                                <i class="fas fa-plus"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <div class="row valign-wrapper">

                    <div class="col s4 m3 l3">
                        <h6><fmt:message key="i18n.publicationDate"/></h6>
                    </div>
                    <div class="col s8 m9 l9">

                        <label for="text-field-filter-publication-date"></label><input
                            id="text-field-filter-publication-date" name="publicationDate" type="text"
                            class="datepicker"
                            value="<fmt:message key="i18n.showMore" />"/>
                    </div>
                </div>

                <div class="row valign-wrapper">
                    <div class="col s4 m3 l3 ">
                        <h6><fmt:message key="i18n.publisher"/></h6>
                    </div>
                    <div class="col s8 m9 l9">
                        <div id="chips-publisher" class="col s12 chips chips-placeholder input-field">
                            <input class="input" placeholder="<fmt:message key="i18n.enterPublisher"/>">
                        </div>
                    </div>
                </div>


            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-green btn-flat">OK</a>
        </div>
    </div>
</div>
<script type="text/javascript">

    let rating = 0;
    let lockRating = false;

    $( document ).ready(function () {

        $('.chips').chips();
        $('#chips-tags').chips({
            secondaryPlaceholder: '+' + '<fmt:message key="i18n.tag" />'
        });

        $('#chips-publisher').chips({
            secondaryPlaceholder: '+' + '<fmt:message key="i18n.publisher" />'
        });

        $('#chips-categories').chips({
            placeholder: '<fmt:message key="i18n.selectCategory" />',
            secondaryPlaceholder: '+' + '<fmt:message key="i18n.category" />',
        });

        $("#add-category-button").on("click", function () {
            let categoriesInstance = M.Chips.getInstance($('#chips-categories'));
            categoriesInstance.addChip({
                tag: $("#category-dropdown").val()
            });
        });

        $("#text-field-filter-publication-date").datepicker({
            format: "yyyy-mm-dd",
            yearRange: [2019, 2019],
            defaultDate: localStorage.getItem("date"),
            setDefaultDate: true,
            container: document.getElementById("search-bar")
        });

        $(".star").on("click", function (element) {
            if (!lockRating) {
                let starId = element.target.id;
                let factor = starId.substr(1, 1);
                let newRating = factor * 20;
                if (newRating > rating) {
                    rating = newRating
                } else {
                    rating = newRating - 20;
                    lockRating = true;
                }

                let frontStars = document.getElementById("front-stars");
                frontStars.style.width = rating + "%";
            } else {
                lockRating = false;
            }
        });

    });

    function onSearch() {
        let searchField = document.getElementById("text-field-search");
        let instance = M.Datepicker.getInstance(document.getElementById("text-field-filter-publication-date"));
        let date = instance.toString();
        if(date === ""){
            date = "2019-01-01";
        }

        let tags = M.Chips.getInstance($('#chips-tags')).chipsData;
        let publisher = M.Chips.getInstance($('#chips-publisher')).chipsData;
        let categories = M.Chips.getInstance($('#chips-categories')).chipsData;
        localStorage.setItem("date",instance.toString());
        localStorage.setItem("rating",rating);
        localStorage.setItem("categories", JSON.stringify(categories));
        localStorage.setItem("tags",JSON.stringify(tags));
        localStorage.setItem("publisher",JSON.stringify(publisher));
        let url = "${fn:escapeXml(pageContext.request.contextPath)}/marketplace/search?" + $.param({searchQuery:searchField.value,date,rating});
        tags.forEach(tag => url+="&tags=" + tag.tag);
        publisher.forEach(publisherItem => url+="&publisher=" + publisherItem.tag);
        categories.forEach(category => url+="&categories=" + category.tag);
        window.location = url;
    }
</script>