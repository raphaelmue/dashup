(function () {
    $.fn.navigationBar = function(parameterObject) {
        if ($(this).length) {
            let navigationBar = new NavigationBar(this, parameterObject);

            $(this).find("li").each(function () {
                $(this).hover(function() {
                    navigationBar.showMenu($(this))
                }, function() {
                    navigationBar.hideMenu($(this))
                });
            });

            $(window).scroll(function() {
                navigationBar.onScroll();
            });

            return navigationBar;
        }
        return null;
    }
} (jQuery));

function NavigationBar(element, parameterObject) {
    this.element = element;

    let defaultParameters = {
        navItemIdPrefix: "nav-item-",
        navMenuIdPrefix: "nav-menu-",
        navGroupIdPrefix: "nav-group-",
        structure: { },
        align: "left",
        hoverColor: "#00658b",
        dropDownIconClass: "fa fa-chevron-down",
        menuClass: "",
        groupClass: "",
        menuItemClass: "",
        animate: true,
        animationDuration: 300,
        animationEasing: "easeOutExpo",
        fixed: {
            isFixed: false,
            containerId: "",
            fixedHeaderClass: "",
            fixedHeaderPositionOff: 0
        }
    };

    this.options = $.extend({}, defaultParameters, parameterObject || {});

    this.init();
}

NavigationBar.prototype.init = function() {
    if (!(this.options.align === "left" || "right" || "center")) {
        console.error("Align must be either \"left\", \"right\" or \"center\"!");
        this.options.align = "left";
    }

    for (let itemId in this.options.structure) {
        let navItemId = "#" + this.options.navItemIdPrefix + itemId;

        // adding dropdown icon
        $(navItemId).append("<span class = \"expand-icon " + this.options.dropDownIconClass + "\"></span>");

        // setting menu container
        $(navItemId).append("<div id=\"" + this.options.navMenuIdPrefix + itemId + "\" class=\"menu-container " + this.options.menuClass + "\"></div>");

        for (let groupId in this.options.structure[itemId].groups) {
            // add groups
            $('#' + this.options.navMenuIdPrefix + itemId).append(
                    "<div id=\"" + this.options.navGroupIdPrefix + groupId + "\" class=\"group " + this.options.groupClass + "\">" +
                        "<h3>" + this.options.structure[itemId].groups[groupId].name + "</h3>" +
                        "<ul></ul>" +
                    "</div>"
                );

            for (let menuItemId in this.options.structure[itemId].groups[groupId].elements) {
                // set menu items
                $("#" + this.options.navGroupIdPrefix + groupId).find('ul').append(
                    "<li id=\"" + this.options.navItemIdPrefix + itemId + "-" + menuItemId + "\" " +
                            "class=\"menu-item " + this.options.menuItemClass + "\">" +
                        "<span class=\"menu-item-icon " + this.options.structure[itemId].groups[groupId].elements[menuItemId].iconClass + "\"></span>" +
                        "<a href=\"" + this.options.structure[itemId].groups[groupId].elements[menuItemId].url + "\">" +
                            this.options.structure[itemId].groups[groupId].elements[menuItemId].name +
                        "</a>" +
                    "</li>"
                );
            }
        }

    }
};

NavigationBar.prototype.onScroll = function() {
    if (this.options.hasOwnProperty("fixed") && this.options.fixed.isFixed) {
        $('#' + this.options.fixed.containerId).toggleClass("fixed-navbar " + this.options.fixed.fixedHeaderClass,
            ($(window).scrollTop() >= this.options.fixed.fixedHeaderPositionOff));
    }

};

NavigationBar.prototype.showMenu = function (element) {
    let item = element.attr("id").substr(this.options.navItemIdPrefix.length);
    let height = this.getMenuHeight(item);
    this.setPositionOfMenu(item);
    $("#" + this.options.navMenuIdPrefix + item)
        .css("visibility", "visible")
        .animate({
            minHeight: height,
            opacity: 1
        }, {
            duration: this.options.animationDuration,
            easing: this.options.animationEasing
        });
};

NavigationBar.prototype.hideMenu = function (element) {
    $("#" + this.options.navMenuIdPrefix + element.attr("id").substr(this.options.navItemIdPrefix.length))
        .animate({
            minHeight: 0,
            opacity: 0
        }, {
            duration: this.options.animationDuration,
            easing: this.options.animationEasing,
            complete: function() {
                $(this).css("visibility", "hidden")
            }
        });
};

NavigationBar.prototype.setPositionOfMenu = function (item) {
    let navItem = $('#' + this.options.navItemIdPrefix + item);
    let positionLeft;

    switch (this.options.align) {
        case "left":
            positionLeft = navItem.offset().left;
            break;
        case "right":
            positionLeft = $(window).width() - (navItem.offset().left + navItem.outerWidth());
            break;
        case "center":
            positionLeft = navItem.offset().left + (navItem.outerWidth() / 2 - $('#' + this.options.navMenuIdPrefix + item).outerWidth() / 2);
            break;
    }

    let positionTop = navItem.offset().top + navItem.outerHeight(true) - $(window).scrollTop() + 22;

    $("#" + this.options.navMenuIdPrefix + item)
        .css("top", positionTop + "px")
        .css((this.options.align === "right" ? "right" : "left"), positionLeft + "px");
};

NavigationBar.prototype.getMenuHeight = function (item) {
    let height = 0;

    $('#' + this.options.navMenuIdPrefix + item).find('.group').each(function() {
        height += $(this).outerHeight(true);
    });

    return height;
};