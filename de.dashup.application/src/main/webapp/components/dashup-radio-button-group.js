import {DashupComponent} from "./dashup-component.js";

export class DashupRadioButtonGroup extends DashupComponent{

    render() {
        return this.html`
            ${this.elements.sort().map((element) => DashupComponent.html`
                <label>
                    <input class="with-gap" name="${element.group}" 
                           type="radio" @click="${() => this.value = `${element.value}`}" 
                           ?checked="${element.checked}" ?disabled="${element.disabled}" />
                    <span>${element.value}</span>
                </label>
            `)}
        `;
    }

    static get properties() {
        return {
            elements: {type: Array},
            value: {type: String}
        };
    }

    constructor() {
        super();
        this.elements = [];
    }

    //Called after elements templated is created -> layout for grid layout within a grid layout is already defined
    firstUpdated() {
        Array.from(this.children).map((element) => {
            this.elements.push(element);
        });
        this.requestUpdate();
    }

    getValue() {
        return this.value ? this.value : null;
    }

}
customElements.define("dashup-radio-button-group",DashupRadioButtonGroup);