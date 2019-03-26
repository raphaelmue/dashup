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

    handleData(data) {
        super.handleData(data);
        this.input.innerHTML = this.getAttribute("value");
    }

    /**
     * Called when web component is shown on screen.
     */
    connectedCallback() {
        // set text and label
        this.input.innerHTML = this.getAttribute("value");
        this.input.addEventListener("change", () => {
            this.value = this.input.value;
        });
        this.inputLabel.innerHTML = this.getAttribute("label");
    }
}

customElements.define("dashup-text-field", TextFieldComponent);