import {DashupComponent} from "./dashup-component.js";

export class DashupTextInput extends DashupComponent{

    render() {
        return DashupComponent.html`
            <input type="text" placeholder="${this.placeholder}" 
                   .value="${this.value ? this.value : ""}" @change="${(evt) => { this.value = evt.target.value;}}" 
                   ?disabled="${this.disabled}"/>
        `;
    }

    static get properties() {
        return {
            value: {type: String, reflect: true},
            placeholder: {type: String},
            disabled: {type: Boolean, reflect: true},
            clear: {type: Boolean, reflect: true}
        };
    }

    getValue() {
        let value = this.value;
        if(this.clear){
            this.value = null;
        }
        return value;
    }

}
customElements.define("dashup-text-input", DashupTextInput);