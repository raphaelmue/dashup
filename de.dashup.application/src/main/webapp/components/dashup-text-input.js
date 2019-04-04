import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";
export class DashupTextInput extends DashupComponent{

    render() {
        return html`
            <input type="text" placeholder="${this.placeholder}" @change="${(evt) => { this.value = evt.target.value;}}"/>
        `;
    }

    static get properties() {
        return {
            label: {type: String, reflect: true},
            value: {type: String, reflect: true},
            placeholder: {type: String}
        };
    }

    constructor() {
        super();
        this.label = "";
        this.value = "";
        this.placeholder = "Enter your text here..."
    }

}
customElements.define("dashup-text-input",DashupTextInput);