const dashupButton = document.currentScript.ownerDocument.querySelector("#dashup-button-template").content;

class ButtonComponent extends DashupComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupButton, true);
        this.shadowRoot.appendChild(this.content);

        this.button = this.shadowRoot.querySelector("#dashup-button");
    }

    /**
     * Called when web component is shown on screen.
     */
    connectedCallback() {
        // setting label
        if (this.hasAttribute("label")) {
            this.button.innerHTML = this.escapeHTML(this.getAttribute("label"));
        }

        // fetching data from api if api is valid
        if (this.hasAttribute("api") && ButtonComponent._isURLValid(this.api)) {
            // add click listener
            this.button.addEventListener("click", () => {
                this._callAPI();
            });
        }
    }

    /**
     * Returns all attributes, that this web component can have.
     * @returns {string[]} array of attribute names
     */
    static get observedAttributes() {
        return ["id", "label", "consumers", "sources", "api", "api-param"];
    }

    /**
     * Calls an an API that is specified in attributes. If this worked, the consumers's method "handleData" is called.
     * @private
     */
    _callAPI() {
        let url = this._createURLWithParameters();
        fetch(url).then(function (response) {
            return response.json();
        }).then((function (response) {
            this.getAttribute("consumers").split(" ").forEach((function (consumer) {
                let consumerElement;
                if (consumer.split(".").length === 2) {
                    let consumerContainer = consumer.split(".")[0],
                        consumerId = consumer.split(".")[1];

                    consumerElement = this.getRootNode().querySelector("#" + consumerContainer)
                        .getShadowRoot().querySelector("#" + consumerId);
                } else {
                    consumerElement = this.getRootNode().querySelector("#" + consumer);
                }
                consumerElement.handleData(response);
            }).bind(this));
        }).bind(this));
    }

    _createURLWithParameters() {
        let url;
        if (this.hasAttribute("api-param")) {
            // append "?" if not exists
            url = this.api;

            // build URL
            let parameters = this.getAttribute("api-param").split(" ");
            if (parameters.length > 0) {
                parameters.forEach(function (parameterIdPair) {
                    let parameterName = parameterIdPair.split(":")[0],
                        parameterId = parameterIdPair.split(":")[1];

                    let parameterValue = this.getRootNode().querySelector("#" + parameterId).getAttribute("value");
                    if (parameterValue !== null) {
                        if (url.includes("?")) {
                            if (!(url.slice(-1) === ("?" || "&"))) {
                                url += "&";
                            }
                        } else {
                            url += "?";
                        }
                        url += parameterName + "=" + parameterValue;
                    }
                }, this);
            }
        } else {
            url = this.api;
        }
        return url;
    }

    /**
     * Checks whether URL is valid
     * @param url URL that will be checked
     * @returns {boolean} true if URL is valid
     * @private
     */
    static _isURLValid(url) {
        let pattern = new RegExp("^(https?:\\/\\/)?"+ // protocol
            "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|"+ // domain id
            "((\\d{1,3}\\.){3}\\d{1,3}))"+ // OR ip (v4) address
            "(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*"+ // port and path
            "(\\?[;&a-z\\d%_.~+=-]*)?"+ // query string
            "(\\#[-a-z\\d_]*)?$","i"); // fragment locator
        return pattern.test(url);
    }

    get id() {
        return this.getAttribute("id");
    }

    set id(val) {
        this.setAttribute("id", val);
    }

    get label() {
        return this.getAttribute("label");
    }

    set label(val) {
        this.setAttribute("label", val);
    }

    get consumers() {
        return this.getAttribute("consumers");
    }

    set consumers(val) {
        this.setAttribute("consumers", val);
    }

    get sources() {
        return this.getAttribute("sources");
    }

    set sources(val) {
        this.setAttribute("sources", val);
    }

    get api() {
        return this.getAttribute("api");
    }

    set api(val) {
        this.setAttribute("api", val);
    }

    get param() {
        return this.getAttribute("param");
    }

    set param(val) {
        this.setAttribute("param", val);
    }

    get apiParam() {
        return this.getAttribute("api-param");
    }

    set apiParam(val) {
        this.setAttribute("api-param", val);
    }
}

customElements.define("dashup-button", ButtonComponent);