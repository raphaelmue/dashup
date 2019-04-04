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
            displayedText: {type: String},
            data: {type: Object},
            path: {type: String}
        };
    }

    constructor() {
        super();
        this.displayedText = "";
        this.data = {};
        this.path = "";
    }

    handleData(data) {
        let value = data;
        let path = this.path.split(" ");
        for(let i = 0; i < path.length; i++) {
            value = value[path[i]];
        }
        this.displayedText = value;
        this.data = data;
    }

}
customElements.define("dashup-display",DashupDisplay);