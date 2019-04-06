import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";

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
            items: {type: Array},
            path: {type: String},
            selectable: {type: Boolean},
            values: {type: Array, attribute: false}
        };
    }
    constructor() {
        super();
        this.items = [];
        this.path = "";
        this.values = [];
    }

    displayData(data) {
        let value = data;
        if(this.path){
            let path = this.path.split(" ");
            for(let i = 0; i < path.length; i++) {
                value = value[path[i]];
            }
        }
        this.items = Array.isArray(value) ? value : [...this.items, value];
    }

    deleteData(data){
        this.items = this.items.filter((item) => !data[this.name].includes(item))  //quick and dirty, actually it should be done via API
    }

    selectEntry(evt){
        if(this.selectable){
            let item = evt.target;
            item.seletced = !item.seletced;
            if(item.seletced) {
                item.setAttribute("selected","");
                this.values.push(item.innerText);
            } else {
                item.removeAttribute("selected");
                this.values.splice(this.values.indexOf(item.innerText),1)
            }
        }
    }

    getValue(){
        return this.values;
    }
}
customElements.define("dashup-list", DashupList);