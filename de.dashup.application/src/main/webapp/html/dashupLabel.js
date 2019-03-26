const dashupLabel = document.currentScript.ownerDocument.querySelector('#dashupLabel').content;

class DisplayComponent extends HTMLElement {

    constructor() {
        super();
        this.attachShadow({mode: 'open'});
        this.content = document.importNode(dashupLabel, true);
        this.shadowRoot.appendChild(this.content);

        this.labelElement = this.shadowRoot.querySelector('#dashup-label');
        this.labelElement.innerHTML = "No Data";
    }

    handleData(data, val) {
        //Workaround, da noch allgemeines API Format festgelegt werden muss
        if (val == "temperature") {
            this.text = data.main.temp + "°C";
        } else {
            this.text = data.weather[0].description;
        }
        this.labelElement.innerHTML = this.text
    }

    static get observedAttributes() {
        return ['text'];
    }

    connectedCallback() {
        if (!this.hasAttribute('name')) {
            this.setAttribute('name', 'default');
        }
        if (!this.hasAttribute('name')) {
            this.setAttribute('name', 'default');
        }
        this.labelElement.innerHTML = this.getAttribute('label') + this.text;
    }

    disconnectedCallback() {
        //NOOP
    }

    adoptedCallback() {
        //NOOP
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case 'text':
                this.labelElement.value = newValue;
                break;
        }
    }

    get name() {
        return this.getAttribute('name');
    }

    set name(val) {
        this.setAttribute('name', val);
    }

    get label() {
        return this.getAttribute('label');
    }

    set label(val) {
        this.setAttribute('label', val);
    }

    get text() {
        return this.getAttribute('text');
    }

    set text(val) {
        this.setAttribute('text', val);
    }

}

customElements.define("dashup-display", DisplayComponent);