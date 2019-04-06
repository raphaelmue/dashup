import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";
export class DashupButton extends DashupComponent{

    render() {
        return html`
            <button class="btn waves-effect waves-light" @click="${this.callAPI}">
                ${this.text}
            </button>
        `;
    }

    // API change check: return !(new RegExp("\\b" + oldValue.replace(new RegExp("%.*?%", "g"), ".*") + "\\b").test(newValue));
    // Returns true if only the placeholders where filled
    static get properties() {
        return {
            text: {type: String},
            api: {type: String, hasChanged: () => {return false;}},
            consumers: {type: Array, hasChanged: () => {return false;}, converter: (consumers) => {return consumers.split(" ")}},
            producers: {type: Array, hasChanged: () => {return false;}, converter: (producers) => {return producers.split(" ")}}
        };
    }

    callAPI() {
        let parameters = this.receiveData();
        let url = this.api;
        for (let name in parameters) {
            url = url.replace(`%${name}%`, parameters[name]);
        }

        fetch(url).then((response) => {
            return response.json();
        }).then((json) => {
            this.dispatchData(json);
        })
    }

    receiveData() {
        let parameters = {};
        this.producers.forEach(((producerName) => {
            let producer = this.getRootNode().querySelector("[name=" + producerName + "]");
            parameters[producer.name] = producer.value;
        }).bind(this));
        return parameters;
    }

    dispatchData(data){
        this.consumers.forEach(((consumerName) => {
            let consumer = this.getRootNode().querySelector("[name=" + consumerName + "]");
            consumer.handleData(data);
        }).bind(this));
    }

}
customElements.define("dashup-button",DashupButton);