$(document).ready(function () {
    PostRequest.getInstance().setHost(true);

    $(".sidenav").sidenav();
    $(".collapsible").collapsible();
    $("select").formSelect();
    $(".modal").modal();

    let undoBtn = $(".undo-button");
    if (undoBtn.length > 0) {
        undoBtn.on("click", function () {
            if (window.location.href.slice(-1) === "#") {
                window.location.href += "undoComplete";
            } else {
                window.location.href += "#undoComplete";
            }
            window.location.reload(false);
        });
    }
});

function getAnchor() {
    let currentUrl = document.URL,
        urlParts = currentUrl.split("#");

    return (urlParts.length > 1) ? urlParts[1] : null;
}

function clearAnchor() {
    window.location = window.location.toString().split("#")[0] + "#";
}