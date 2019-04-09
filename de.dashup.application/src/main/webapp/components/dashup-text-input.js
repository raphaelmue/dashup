import {LitElement, html, css} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {DashupComponent} from "./dashup-component.js";
export class DashupTextInput extends DashupComponent{

    render() {
        return html`
            <input type="text" placeholder="${this.placeholder}" 
                   .value="${this.value ? this.value : ""}" @change="${(evt) => { this.value = evt.target.value;}}" 
                   ?disabled="${this.disabled}"/>
        `;
    }

    static get properties() {
        return {
            value: {type: String, reflect: true},
            placeholder: {type: String},
            disabled: {type: Boolean, reflect: true}
        };
    }

    getValue() {
        let response = this.value ? this.value : null;
        this.value = String("");
        return response;
    }

}
customElements.define("dashup-text-input", DashupTextInput);