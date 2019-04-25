import {html} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {DashupComponent} from "./dashup-component.js";
import {MessageBroker} from "./message-broker.js";

export class DashupList extends DashupComponent{

    render() {
        return html`
            <style>
                a.collection-item[selected] {
                background: rgb(224, 224, 224);
            }
            </style>
            <div class="collection">
                ${this.items.length ? 
                    this.items.map((item) => {
                        return html`
                            <a class="collection-item" @click="${this.selectEntry}">
                                ${item}
                            </a>`;}) : 
                    html`<a class="collection-item">No elements in the list</a>`}
            </div>
        `;
    }

    static get properties() {
        return {
            items: {type: Array, attribute: false},
            selectedItems: {type: Array, attribute: false},
            selectable: {type: Boolean, reflect: true}
        };
    }
    constructor() {
        super();
        this.items = [];
        this.selectedItems = [];
    }

    handleData(data) {
        if(data.data){
            switch(data.mode) {
                case MessageBroker.MessageMode.DISPLAY: this.displayData(data.data); break;
                case MessageBroker.MessageMode.ADD: this.addData(data.data); break;
                case MessageBroker.MessageMode.DELETE: this.deleteData(data.data); break;
            }
            this.requestUpdate();
        }
    }

    displayData(data) {
        this.items = Array.isArray(data) ? data : [data];
    }

    addData(data){
        this.items = Array.isArray(data) ? [...this.items, ...data] : [...this.items, data];
    }

    deleteData(data){
        this.items = this.items.filter((item) => Array.isArray(data) ? !data.includes(item) : item !== data);
    }

    selectEntry(evt){
        if(this.selectable){
            let item = evt.target;
            item.seletced = !item.seletced;
            if(item.seletced) {
                item.setAttribute("selected","");
                this.selectedItems.push(item.innerText);
            } else {
                item.removeAttribute("selected");
                this.selectedItems.splice(this.selectedItems.indexOf(item.innerText),1);
            }
        }
    }

    getValue(){
        return this.selectedItems;
    }
}
customElements.define("dashup-list", DashupList);