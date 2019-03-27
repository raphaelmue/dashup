class InputComponent extends HTMLElement {

    constructor() {
        super();
    }

    handleData(data) {
        if (this.hasAttribute("api-key")) {
            let keys = this.getAttribute("api-key").split(".");
            let error = false;
            keys.forEach(function (key) {
                if (!error) {
                    if (data.hasOwnProperty(key)) {
                        data = data[key];
                    } else {
                        error = true;
                    }
                }
            });

            if (error) {
                console.error("Invalid api-key attribute. Element ID: " + this.getAttribute("id"));
            }

            this.setAttribute("value", data);
            this.setText();
        }
    }

    /**
     * Implementation required
     */
    setText() { }

    /**
     * Returns all attributes, that this web component can have.
     * @returns {string[]} array of attribute names
     */
    static get observedAttributes() {
        return ["id", "label", "value"];
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

    get value() {
        return this.getAttribute("value");
    }

    set value(val) {
        this.setAttribute("value", val);
    }

    get apiKey() {
        return this.getAttribute("api-key");
    }

    set apiKey(val) {
        this.setAttribute("api-key", val);
    }
}