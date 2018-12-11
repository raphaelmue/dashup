PostRequest.INSTANCE = null;
PostRequest.URL_DEPLOY = "https://dashup.de/server/";
PostRequest.URL_LOCAL = "http://localhost:9004/";
PostRequest.URL = "";

function PostRequest() {

}

PostRequest.getInstance = function() {
    if (this.INSTANCE === null) {
        this.INSTANCE = new PostRequest();
    }
    return this.INSTANCE;
};

PostRequest.prototype = {
    constructor: PostRequest,

    setHost: function(local) {
        PostRequest.URL = local ? PostRequest.URL_LOCAL : PostRequest.URL_DEPLOY;
    },

    /**
     * Performs an PostRequest
     * @param action action which will be performed on server side
     * @param data parameters of action
     * @param callback callback function (result as parameter)
     */
    make: function(action, data) {
        let htmlForm = "<form id=\"submission-form\" method=\"post\" action=" + PostRequest.URL + action + ">"

        jQuery.each(data, function(key, val) {
            htmlForm += "<input type=\"hidden\" name=\"" + key + "\" value=\"" + val + "\"/>";
        });

        htmlForm += "</form>";
        let form = $(htmlForm);
        $("body").append(form);
        $("#submission-form").submit();
    },
};