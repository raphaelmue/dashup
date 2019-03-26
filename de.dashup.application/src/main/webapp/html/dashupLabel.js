const dashupLabel = document.currentScript.ownerDocument.querySelector("#dashup-label-template").content;

class DisplayComponent extends InputComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupLabel, true);
        this.shadowRoot.appendChild(this.content);

        this.labelElement = this.shadowRoot.querySelector("#dashup-label");
        this.labelElement.innerHTML = "No Data";
    }

    handleData(data) {
        super.handleData(data);
        this.labelElement.innerHTML = this.getAttribute("label") + " " + this.getAttribute("value");
    }

    /**
     * Called when web component is shown on screen.
     */
    connectedCallback() {
        this.labelElement.innerHTML = this.getAttribute("label") + this.text;
    }
}

customElements.define("dashup-display", DisplayComponent);