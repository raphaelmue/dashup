const dashupLabel = document.currentScript.ownerDocument.querySelector("#dashup-label-template").content;

class DisplayComponent extends InputComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupLabel, true);
        this.shadowRoot.appendChild(this.content);

        this.labelElement = this.shadowRoot.querySelector("#dashup-label-label");
        this.valueElement = this.shadowRoot.querySelector("#dashup-label-value");
        this.quantityElement = this.shadowRoot.querySelector("#dashup-label-quantity");
    }

    /**
     * Returns all attributes, that this web component can have.
     * @returns {string[]} array of attribute names
     */
    static get observedAttributes() {
        let attributes = super.observedAttributes;
        attributes.push("quantity");
        return attributes;
    }

    /**
     * Called when web component is shown on screen. Used for registering events.
     */
    connectedCallback() {
    }

    /**
     * Called when an attribute has changed.
     * @param name name of the attribute
     * @param oldValue old value of the attribute
     * @param newValue new value of the attribute
     */
    attributeChangedCallback(name, oldValue, newValue) {
        if (newValue !== oldValue && newValue !== null) {
            switch (name) {
                case "label":
                    this.labelElement.innerHTML = this.escapeHTML(newValue + " ");
                    break;
                case "value":
                    this.valueElement.innerHTML = this.escapeHTML(newValue + this.getAttribute("quantity"));
                    break;
                case "quantity":
                    if (this.getAttribute("value") !== null) {
                        this.quantityElement.innerHTML += this.escapeHTML(newValue);
                    }
                    break;
            }
        }
    }

    get quantity() {
        return this.getAttribute("quantity");
    }

    set quantity(val) {
        this.setAttribute("quantity", val);
    }
}

customElements.define("dashup-display", DisplayComponent);