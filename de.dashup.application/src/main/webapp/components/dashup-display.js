import {DashupComponent} from "./dashup-component.js";

export class DashupDisplay extends DashupComponent {

    render() {
        let style = {"margin-left": "0px"};
        return DashupComponent.html`
            <div class="collection">
                <a class="collection-item">
                    <u>${this.label}:</u>
                    <strong>
                        <span class="badge" style="${DashupComponent.styleMap(style)}">${this.displayedText} ${this.displayedText ? this.quantity : ""}</span>
                    </strong>
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