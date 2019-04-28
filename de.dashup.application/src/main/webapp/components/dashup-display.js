import {DashupComponent} from "./dashup-component.js";

export class DashupDisplay extends DashupComponent {

    render() {
        return DashupComponent.html`
            <div class="collection">
                <a class="collection-item">
                ${this.label}
                <span class="badge">${this.displayedText} ${this.displayedText ? this.quantity : ""}</span>
                </a>
            </div>
        `;
    }

    static get styles() {
        return DashupComponent.css`
            .collection :hover{
                background-color: rgb(255, 255, 255) !important;
            }
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

customElements.define("dashup-display", DashupDisplay);