import {DashupComponent} from "./dashup-component.js";

export class DashupGridLayout extends DashupComponent {

    render() {
        return DashupComponent.html`
            <div class="container">
                ${this.elements.map((row) => DashupComponent.html`
                    <div class="row">
                        ${row.map((element) => {
                            let size = element.layout.size;
                            let offset = element.layout.offset;
                            let classes = {col: true};
                            let sizes = ["s", "m", "l", "xl"];
                            sizes.forEach((element) => {
                                if (size) {
                                    classes[element + size] = true;
                                } else {
                                    classes[element + "4"] = true;
                                }
                                if (offset) {
                                    classes["offset-" + element + offset] = true;
                                }
                            });
                            return DashupComponent.html`
                                <div class="${DashupComponent.classMap(classes)}">
                                    <slot name="${this.getSlotName(element)}"></slot>
                                </div>
                            `;
        })}
                    </div>
                `)}
            </div>
        `;
    }

    static get properties() {
        return {
            elements: {
                type: Array, hasChanged: () => {
                    return false;
                }
            }
        };
    }

    constructor() {
        super();
        this.elements = [];
    }

    //Called after elements templated is created -> layout for grid layout within a grid layout is already defined
    firstUpdated() {
        Array.from(this.children).map((element) => {
            let row = element.layout.row;
            if (!this.elements[row]) {
                this.elements[row] = [];
            }
            element.setAttribute("slot", this.getSlotName(element));
            this.elements[row].push(element);
        });
        this.requestUpdate();
    }

    getSlotName(element) {
        let keyData = [element.name, element.layout.row, element.layout.offset, element.layout.size];
        return keyData.join("-");
    }

}

customElements.define("dashup-grid-layout", DashupGridLayout);