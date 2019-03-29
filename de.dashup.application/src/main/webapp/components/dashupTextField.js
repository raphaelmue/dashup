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
    }

    setText() {
        if  (this.hasAttribute("label")) {
            this.inputLabel.innerHTML = escape(this.getAttribute("label"));
        }
        if (this.hasAttribute("value")) {
            this.input.innerHTML = escape(this.getAttribute("value"));
        }
    }

    /**
     * Called when web component is shown on screen.
     */
    connectedCallback() {
        // set text and label
        this.setText();
        this.input.addEventListener("change", () => {
            this.value = this.input.value;
        });
    }
}

customElements.define("dashup-text-field", TextFieldComponent);