import {LitElement, html, css} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {DashupComponent} from "./dashup-component.js";
export class DashupSwitch extends DashupComponent{

    render() {
        return html`
            <div class="switch">
                <label>
                    ${this.valueLeft}
                    <input type="checkbox" ?checked="${this.active}" ?disabled="${this.disabled}">
                    <span class="lever"></span>
                    ${this.valueRight}
                </label>
            </div>
        `;
    }

    static get properties() {
        return {
            valueLeft: {type: String, reflect: true},
            valueRight: {type: String, reflect: true},
            active: {type: Boolean, reflect: true},
            disabled: {type: Boolean, reflect: true}
        };
    }

    getValue() {
        return this.active ? this.valueRight : this.valueLeft;
    }

}
customElements.define("dashup-switch", DashupSwitch);