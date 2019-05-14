import {DashupComponent} from "./dashup-component.js";

export class DashupRadioButton extends DashupComponent {

    static get properties() {
        return {
            group: {type: String, reflect: true},
            value: {type: String, reflect: true},
            checked: {type: Boolean, reflect: true},
            disabled: {type: Boolean, reflect: true}
        };
    }

    getValue() {
        if(this.checked){
            return this.value
        }
        return null;
    }

}

customElements.define("dashup-radio-button", DashupRadioButton);