import {DashupComponent} from "./dashup-component.js";

export class DashupSwitch extends DashupComponent {

    render() {
        return DashupComponent.html`
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
        if(this.active){
            return this.valueRight;
        }
        return this.valueLeft;
    }

}

customElements.define("dashup-switch", DashupSwitch);