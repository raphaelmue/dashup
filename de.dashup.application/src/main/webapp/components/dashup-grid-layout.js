import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module'
import {DashupComponent} from "./dashup-component.js";
export class DashupGridLayout extends DashupComponent{

    render() {
        return html`
            <div class="container">
                ${this.elements.map((row) => html`
                    <div class="row">
                        ${row.map((element) => {
            let size = element.layout.size;
            let offset = element.layout.offset;
            return html`
                                <div class="col s${size} m${size} l${size} xl${size} 
                                            offset-s${offset} offset-m${offset} offset-l${offset} offset-xl${offset}">
                                    <slot name="${this.getSlotName(element)}"></slot>
                                </div>
                            `
        })}
                    </div>
                `)}
            </div>
        `;
    }

    static get properties() {
        return {
            elements: {type: Array}
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
            if(!this.elements[row]){
                this.elements[row] = new Array();
            }
            element.setAttribute("slot", this.getSlotName(element));
            this.elements[row].push(element);
        });
        this.requestUpdate();
    }

    getSlotName(element){
        let keyData = [element.name, element.layout.row, element.layout.offset, element.layout.size];
        return keyData.join("-");
    }

}
customElements.define("dashup-grid-layout",DashupGridLayout);