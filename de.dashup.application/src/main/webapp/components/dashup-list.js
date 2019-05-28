import {DashupComponent} from "./dashup-component.js";
import {MessageBroker} from "./message-broker.js";

export class DashupList extends DashupComponent {

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
                            </a>`;
                    }) :
                    DashupComponent.html`<a class="collection-item">No elements in the list</a>`}
            </div>
        `;
    }

    static get properties() {
        return {
            items: {type: Array, attribute: true},
            selectable: {type: Boolean, reflect: true}
        };
    }

    constructor(){
        super();
        this.items = [];
    }

    handleData(data) {
        switch (data.mode) {
            case MessageBroker.MessageMode.ADD:
                this.addData(data.data);
                break;
            case MessageBroker.MessageMode.DELETE:
                this.deleteData();
                break;
        }
        this.requestUpdate();
    }

    addData(data) {
        if (Array.isArray(data)) {
            data.filter(element => {
                for(let item of this.items){
                    if(item.content === element.content){
                        return false;
                    }
                }
                return true;
            }).forEach(element => this.items.push(element));
            this.requestUpdate();
        } else if (typeof data === "object") {
            let position = -1;
            this.items.find((element, index) => {
                if (element.content.split(":")[0] === data.category) {
                    position = index;
                    return true;
                }
                return false;
            });
            if (position >= 0) {
                let categoryAmount = this.items[position].content.match(new RegExp(/[0-9]+/))[0];
                let newAmount = Number(data.amount) + Number(categoryAmount);
                this.items[position] = {content: `${data.category}: ${newAmount} €`, selected: false};
                this.requestUpdate();
            } else {
                if(data.amount !== undefined){
                    this.items = [...this.items, {content: data.category + ": " + (data.amount) + " €", selected: false}];
                }
            }
        } else {
            let contains = false;
            for(let item of this.items){
                if(item.content === data){
                    contains = true;
                }
            }
            if(!contains){
                this.items = [...this.items, {content: data, selected: false}];
            }
        }
    }

    deleteData() {
        let sizeBefore = this.items.length;
        this.items = this.items.filter((item) => !item.selected);
        if(this.items.length === sizeBefore){
            this.items = [];
        }
    }

    selectEntry(evt) {
        if (this.selectable) {
            let item = this.items.filter((item) => item.content === evt.target.innerText)[0];
            if(item.selected === undefined){
                item.selected = true;
            } else {
                item.selected = !item.selected;
            }
            this.requestUpdate();
        }
    }

    getValue() {
        return this.items ? this.items : null;
    }
}

customElements.define("dashup-list", DashupList);