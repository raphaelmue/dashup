import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";
export class DashupTextInput extends DashupComponent{

    render() {
        return html`
            <div class="input-field" style="margin: 0">
                <input name="dashup-input" type="text" class="input-css"
                        placeholder="${this.placeholder}" 
                        @change="${(evt) => {
                            this.value = evt.target.value;
                        }}"/>
                <label for="dashup-input">${this.label}</label>
            </div>
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
        this.value = null;
    }

}
customElements.define("dashup-text-input",DashupTextInput);