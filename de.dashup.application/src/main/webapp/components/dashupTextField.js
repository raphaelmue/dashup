const dashupTextField = document.currentScript.ownerDocument.querySelector("#dashup-input-template").content;

class TextFieldComponent extends InputComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupTextField, true);
        this.shadowRoot.appendChild(this.content);

        this.input = this.shadowRoot.querySelector("#dashup-input");
        this.inputLabel = this.shadowRoot.querySelector("#dashup-input-label");
    }

    /**
     * Called when web component is shown on screen. Used for registering events.
     */
    connectedCallback() {
        this.input.addEventListener("change", () => {
            this.value = this.input.value;
        });
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
                    this.inputLabel.innerHTML = this.escapeHTML(newValue);
                    break;
                case "value":
                    this.input.value = this.escapeHTML(newValue);
                    break;
            }
        }
    }
}

customElements.define("dashup-text-field", TextFieldComponent);