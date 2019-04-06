import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";

const  ButtonModes = { DISPLAY: "display", DELETE: "delete" };

export class DashupButton extends DashupComponent{

    render() {
        return html`
            <button class="btn waves-effect waves-light" @click="${this.handleAction}" 
                    ?disabled="${this.disabled}">${this.text}</button>
        `;
    }

    // API change check: return !(new RegExp("\\b" + oldValue.replace(new RegExp("%.*?%", "g"), ".*") + "\\b").test(newValue));
    // Returns true if only the placeholders where filled
    static get properties() {
        return {
            text: {type: String},
            api: {type: String, hasChanged: () => {return false;}},
            consumers: {type: Array, hasChanged: () => {return false;}, converter: (consumers) => {return consumers.split(" ")}},
            producers: {type: Array, hasChanged: () => {return false;}, converter: (producers) => {return producers.split(" ")}},
            mode: {type: String},
            disabled: {type: Boolean, reflect: true}
        };
    }

    constructor() {
        super();
        this.mode = ButtonModes.DISPLAY;
    }

    handleAction() {
        let data = this.receiveData();
        if(this.api){
            let url = this.api;
            for (let name in data) {
                url = url.replace(`%${name}%`, data[name]);
            }

            fetch(url).then((response) => {
                return response.json();
            }).then((json) => {
                this.dispatchData(json);
            })
        } else {
            this.dispatchData(data);
        }
    }

    receiveData() {
        let data = {};
        this.producers.forEach(((producerName) => {
            let producer = this.getRootNode().querySelector("[name=" + producerName + "]");
            data[producer.name] = producer.getValue();
        }).bind(this));
        return data;
    }

    dispatchData(data){
        this.consumers.forEach(((consumerName) => {
            let consumer = this.getRootNode().querySelector("[name=" + consumerName + "]");
            switch(this.mode) {
                case ButtonModes.DISPLAY:
                    consumer.displayData(data);
                    break;
                case ButtonModes.DELETE :
                    consumer.deleteData(data);
                    break;
            }
        }).bind(this));
    }

}
customElements.define("dashup-button",DashupButton);