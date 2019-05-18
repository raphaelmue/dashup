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
        if (data.data) {
            switch (data.mode) {
                case MessageBroker.MessageMode.ADD:
                    this.addData(data.data);
                    break;
                case MessageBroker.MessageMode.DELETE:
                    this.deleteData(data.data);
                    break;
            }
            this.requestUpdate();
        }
    }

    addData(data) {
        if (Array.isArray(data)) {
            this.items = [...this.items, ...data];
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
                this.items[position] = {content: `${data.category}: ${newAmount} €`};
                this.requestUpdate();
            } else {
                this.items = [...this.items, {content: data.category + ": " + (data.amount) + " €"}];
            }
        } else {
            this.items = [...this.items, {content: data}];
        }
    }

    deleteData(data) {
        this.items = this.items.filter((item) => !data.includes(item));
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
        return this.selectable ? this.items.filter((item) => item.selected) : this.items;
    }
}

customElements.define("dashup-list", DashupList);