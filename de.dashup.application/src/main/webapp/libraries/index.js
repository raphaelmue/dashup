$(document).ready(function () {
    PostRequest.getInstance().setHost(true);

    $(".sidenav").sidenav();

    $(".collapsible").collapsible();
    $("select").formSelect();
    $(".modal").modal();
});

function getAnchor() {
    let currentUrl = document.URL,
        urlParts   = currentUrl.split("#");

    return (urlParts.length > 1) ? urlParts[1] : null;
}

function clearAnchor() {
    window.location = window.location.toString().split("#")[0] + "#";
}