import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
import {DashupComponent} from "./dashup-component.js";
export class DashupButton extends DashupComponent{

    render() {
        return html`
            <button id="dashup-button" class="btn waves-effect waves-light" @click="${this.callAPI}">
                ${this.text}
            </button>
        `;
    }

    static get properties() {
        return {
            text: {type: String},
            api: {type: Object},
            consumers: {type: Array, converter: (consumers) => {return consumers.split(" ")}}
        };
    }

    constructor() {
        super();
        this.text = "Test";
        this.api = {};
    }

    callAPI() {
        let params = "";
        if(this.api.params){    //[{key: ..., value:...},...]
            params = '?';
            this.api.params.forEach((key) => {
                params.append(key + '=' + this.api.params[key] + "&");
            });
        }
        let url = this.api.address + params;
        if(this.validateURL(url)){
            fetch(url).then((response) => {
                return response.json();
            }).then((json) => {
                this.dispatchData(json);
            })
        } else {
            console.log('No valid URL!');
        }
    }

    dispatchData(data){
        this.consumers.forEach(((consumerName) => {
            let consumer = this.getRootNode().querySelector("[name=" + consumerName + "]");
            consumer.handleData(data);
        }).bind(this));
    }

    validateURL(url) {
        let pattern = new RegExp("^(https?:\\/\\/)?"+ // protocol
            "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|"+ // domain id
            "((\\d{1,3}\\.){3}\\d{1,3}))"+ // OR ip (v4) address
            "(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*"+ // port and path
            "(\\?[;&a-z\\d%_.~+=-]*)?"+ // query string
            "(\\#[-a-z\\d_]*)?$","i"); // fragment locator
        return pattern.test(url);
    }

}
customElements.define("dashup-button",DashupButton);