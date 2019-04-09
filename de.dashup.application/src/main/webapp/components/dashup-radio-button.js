import {LitElement, html, css} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {DashupComponent} from "./dashup-component.js";
export class DashupRadioButton extends DashupComponent{

    static get properties() {
        return {
            group: {type: String, reflect: true},
            value: {type: String, reflect: true},
            checked: {type: Boolean, reflect: true},
            disabled: {type: Boolean, reflect: true}
        };
    }

    getValue() {
        return this.checked ? this.value : null;
    }

}
customElements.define("dashup-radio-button", DashupRadioButton);