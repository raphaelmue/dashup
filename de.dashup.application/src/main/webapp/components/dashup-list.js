import {DashupComponent} from "./dashup-component.js";
import {MessageBroker} from "./message-broker.js";

export class DashupList extends DashupComponent{

    render() {
        return DashupComponent.html`
            <style>
                a.collection-item[selected] {
                background: rgb(224, 224, 224);
            }
            </style>
            <div class="collection">
                ${this.items.length ? 
                    this.items.map((item) => {
                        return DashupComponent.html`
                                    <a class="collection-item" @click="${this.selectEntry}" ?selected="${item.selected}">
                                        ${item.content}
                                    </a>`;}) :
                               DashupComponent.html`<a class="collection-item">No elements in the list</a>`}
            </div>
        `;
    }

    static get properties() {
        return {
            items: {type: Array, attribute: false},
            selectable: {type: Boolean, reflect: true}
        };
    }
    constructor() {
        super();
        this.items = [];
    }

    handleData(data) {
        if(data.data){
            switch(data.mode) {
                case MessageBroker.MessageMode.ADD: this.addData(data.data); break;
                case MessageBroker.MessageMode.DELETE: this.deleteData(data.data); break;
            }
            this.requestUpdate();
        }
    }

    addData(data){
        this.items = Array.isArray(data) ? [...this.items, ...data] : [...this.items, {content: data, selected: false}];
    }

    deleteData(data){
        this.items = this.items.filter((item) => !data.includes(item));
    }

    selectEntry(evt){
        if(this.selectable){
            let item = this.items.filter((item) => item.content == evt.target.innerText)[0];
            item.selected = !item.selected;
            this.requestUpdate();
        }
    }

    getValue(){
        return this.items.filter((item) => item.selected);
    }
}
customElements.define("dashup-list", DashupList);