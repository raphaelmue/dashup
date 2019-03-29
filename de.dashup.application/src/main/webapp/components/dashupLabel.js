const dashupLabel = document.currentScript.ownerDocument.querySelector("#dashup-label-template").content;

class DisplayComponent extends InputComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupLabel, true);
        this.shadowRoot.appendChild(this.content);

        this.labelElement = this.shadowRoot.querySelector("#dashup-label");
    }

    handleData(data) {
        super.handleData(data);
    }

    setText() {
        if (this.hasAttribute("label")) {
            this.labelElement.innerHTML = escape(this.getAttribute("label") + " ");
        }
        if (this.hasAttribute("value")) {
            this.labelElement.innerHTML += escape(this.getAttribute("value"));
            if (this.hasAttribute("quantity")) {
                this.labelElement.innerHTML += escape(this.getAttribute("quantity"));
            }
        }
    }

    /**
     * Called when web component is shown on screen.
     */
    connectedCallback() {
        this.setText();
    }

    get quantity() {
        return this.getAttribute("quantity");
    }

    set quantity(val) {
        this.setAttribute("quantity", val);
    }
}

customElements.define("dashup-display", DisplayComponent);