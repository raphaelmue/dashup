import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";
export class DashupDisplay extends DashupComponent{

    render() {
        return html`
            <span>${this.displayedText}</span>
        `;
    }

    static get properties() {
        return {
            data: {type: Object},
            displayedText: {type: String}
        };
    }

    constructor() {
        super();
        this.data = {};
        this.displayedText = "";
    }

    handleData(data) {

    }

}
customElements.define("dashup-display",DashupDisplay);