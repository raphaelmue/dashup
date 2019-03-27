const dashupButton = document.currentScript.ownerDocument.querySelector("#dashup-button-template").content;

class ButtonComponent extends HTMLElement {

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
        this.button.innerHTML = this.getAttribute("label");

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
        let url = this._createURLWithParameters(),
            that = this;
        fetch(url).then(function (response) {
            return response.json();
        }).then(function (response) {
            that.getAttribute("consumers").split(" ").forEach(function (consumer) {
                let consumerElement;
                if (consumer.split(".").length === 2) {
                    let consumerContainer = consumer.split(".")[0],
                        consumerId = consumer.split(".")[1];

                    consumerElement = that.getRootNode().querySelector("#" + consumerContainer)
                        .getShadowRoot().querySelector("#" + consumerId);
                } else {
                    consumerElement = that.getRootNode().querySelector("#" + consumer);
                }
                consumerElement.handleData(response);
            });
        });
    }

    _createURLWithParameters() {
        let url,
            that = this;
        if (this.hasAttribute("api-param")) {
            // append "?" if not exists
            url = this.api;

            // build URL
            let parameters = this.getAttribute("api-param").split(" ");
            parameters.forEach(function (parameterIdPair) {
                let parameterName = parameterIdPair.split(":")[0],
                    parameterId = parameterIdPair.split(":")[1];

                let parameterValue = that.getRootNode().querySelector("#" + parameterId).getAttribute("value");
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
            });
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
        let regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
        return regexp.test(url);
    }

    get name() {
        return this.getAttribute("name");
    }

    set name(val) {
        this.setAttribute("name", val);
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