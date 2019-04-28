import {DashupComponent} from "./dashup-component.js";

export class DashupCheckbox extends DashupComponent{

    render() {
        return DashupComponent.html`
            <label>
                <input type="checkbox" class="filled-in" ?checked="${this.checked}" ?disabled="${this.disabled}" />
                <span>${this.value}</span>
            </label>
        `;
    }

    static get properties() {
        return {
            value: {type: String, reflect: true},
            checked: {type: Boolean, reflect: true},
            disabled: {type: Boolean, reflect: true}
        };
    }

    getValue() {
        return this.checked ? this.value : null;
    }

}
customElements.define("dashup-checkbox", DashupCheckbox);