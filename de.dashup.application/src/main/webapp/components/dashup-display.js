import {LitElement, html, css} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {DashupComponent} from "./dashup-component.js";
export class DashupDisplay extends DashupComponent{

    render() {
        return html`
            <div class="collection">
                <a class="collection-item">
                ${this.label}
                <span class="badge">${this.displayedText} ${this.displayedText ? this.quantity : ""}</span>
                </a>
            </div>
        `;
    }

    static get properties() {
        return {
            label: {type: String},
            displayedText: {type: String},
            quantity: {type: String}
        };
    }

    handleData(data) {
        this.displayedText = data.data;
    }

}
customElements.define("dashup-display",DashupDisplay);