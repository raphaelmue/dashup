import {DashupComponent} from "./dashup-component.js";
import {MessageBroker} from "../libraries/components/message-broker.js";

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
            api: {
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
                type: Object, hasChanged: () => {
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
        let producerData = null;
        let apiData = null;
        if (this.producers) {
            producerData = this.getProducerData();
        }
        if (this.api) {
            apiData = await this.getAPIData();
        }
        this.dispatchData({...producerData, ...apiData});
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
        let url = this.api;
        for (let param of this.params) {
            let producer = this.getRootNode().querySelector("[name=" + param + "]");
            url = url.replace(`%${producer.name}%`, producer.getValue());
        }

        return new Promise((resolve, reject) => {
            fetch(url).then((response) => {
                return response.json();
            }).then((json) => {
                resolve({apiData: json});
            }, () => reject());
        });
    }

    dispatchData(data) {
        for (let consumer in this.consumers) {
            if ({}.hasOwnProperty.call(this.consumers, consumer)) {
                let consumerControl = this.getRootNode().querySelector("[name=" + consumer + "]");
                consumerControl.handleData({
                    data: MessageBroker.getDataFromPath(this.consumers[consumer], data),
                    mode: this.mode
                });
            }
        }
    }

}

customElements.define("dashup-button", DashupButton);