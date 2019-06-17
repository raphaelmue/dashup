import {DashupComponent} from "./dashup-component.js";
import {MessageBroker} from "./message-broker.js";

export class DashupButton extends DashupComponent {

    render() {
        return DashupComponent.html`
            <button class="btn waves-effect waves-light" @click="${this.handleAction}" 
                    ?disabled="${this.disabled}">${this.text}</button>
        `;
    }

    static get properties() {
        return {
            text: {type: String, reflect: true},
            disabled: {type: Boolean, reflect: true},
            handleOnStart: {type: Boolean, reflect: true},
            mode: {
                type: String, hasChanged: () => {
                    return false;
                }
            },
            dataAPI: {
                type: String, hasChanged: () => {
                    return false;
                }
            },
            storageAPI: {
                type: String, hasChanged: () => {
                    return false;
                }
            },
            params: {
                type: Array, hasChanged: () => {
                    return false;
                }, converter: (params) => {
                    return params.split(" ");
                }
            },
            consumers: {
                type: Array, hasChanged: () => {
                    return false;
                }
            },
            producers: {
                type: Array, hasChanged: () => {
                    return false;
                }, converter: (producers) => {
                    return producers.split(" ");
                }
            },
        };
    }

    constructor() {
        super();
        this.mode = MessageBroker.MessageMode.DISPLAY;
        document.addEventListener("DOMContentLoaded", (() => {
            if(this.handleOnStart) {
                this.handleAction();
            }
        }).bind(this));
    }

    async handleAction() {
        let producerData = this.producers ? this.getProducerData() : null;
        let apiData = this.dataAPI ? await this.getAPIData() : null;
        this.dispatchData({...producerData, ...apiData});
    }

    builtURL() {
        let url = this.dataAPI;
        if(this.params){
            for(let param of this.params) {
                let producer = this.getRootNode().querySelector("[name=" + param + "]");
                url = url.replace(`%${producer.name}%`, producer.getValue());
            }
        }
        return url;
    }

    getProducerData() {
        let data = {};
        this.producers.forEach(((producerName) => {
            let producer = this.getRootNode().querySelector("[name=" + producerName + "]");
            data[producer.name] = producer.getValue();
        }).bind(this));
        return data;
    }

    getAPIData() {
        return new Promise((resolve, reject) => {
            fetch(this.builtURL()).then((response) => response.json()).then((json) => {
                resolve({apiData: json});
            }).catch(err => {
                resolve({apiData: null});
            });
        });
    }

    dispatchData(data) {
        for (let consumer of this.consumers) {
            let name = Object.keys(consumer)[0];
            let consumerControl = this.getRootNode().querySelector("[name=" + name + "]");
            let resolvedData = MessageBroker.getDataFromPath(consumer[name], data);
            if(resolvedData){
                consumerControl.handleData({
                    data: resolvedData,
                    mode: this.mode
                });
            }
        }

        if(this.storageAPI){
            let uniqueConsumers = [];
            for (let consumer of this.consumers) {
                let name = Object.keys(consumer)[0];
                let consumerControl = this.getRootNode().querySelector("[name=" + name + "]");
                if(!uniqueConsumers.includes(name)){
                    uniqueConsumers.push(name);
                    let data = {};
                    data[name] = consumerControl.getValue();
                    fetch(this.storageAPI, {
                        method: "POST",
                        body: JSON.stringify(data),
                        headers: {
                            "Content-Type": "application/json"
                        }
                    }).catch(() => {
                        console.log("An error occurred");
                    });
                }
            }
        }
    }
}
customElements.define("dashup-button", DashupButton);