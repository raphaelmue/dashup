import {DashupComponent} from "./dashup-component.js";
import {MessageBroker} from "../libraries/components/message-broker.js";

export class DashupChart extends DashupComponent {

    render() {

        let maxValue = this.data.sort((element1, element2) => {
            return element2.value - element1.value
        })[0];
        maxValue = maxValue ? maxValue.value : 0;
        let heightMap = new Map();
        this.data.forEach((element) => {
            heightMap.set(element.category, {height: (element.value / maxValue) * 100 + "%"})
        });
        let caption = {left: (55 - 7.5 * this.data.length) + "%"};

        //can be enhanced with multiple bars per category later on
        return DashupComponent.html`
            <style>
               ${this.data.map((element, index) => {
                return DashupComponent.html`
                    #q-graph #${element.category} {
                        left: ${index * (100 / this.data.length)}%;
                        width: ${100 / this.data.length}%;
                    }`
                })}
            </style>

            <table id="q-graph">
                <caption>${this.title}</caption>
                <tbody>
                    ${this.data.map(element => DashupComponent.html`
                        <tr class="qtr" id="${element.category}">
                        <th scope="row" style="${DashupComponent.styleMap(caption)}">${element.category}</th>
                        <td class="category bar" style="${DashupComponent.styleMap(heightMap.get(element.category))}"><p>${element.value}</p></td>
                        </tr>
                    `)}
                </tbody>
            </table>
            <div id="ticks">
                ${(() => {
                    let ticks = [];
                    for (let i = 1; i <= 5; i++) {
                        ticks.push(DashupComponent.html`<div class="tick" style="height: 20.5%;"><p>${Math.ceil(maxValue / 5) * i}</p></div>`);
                    }
                    return ticks.reverse();
                })()}
            </div>
        `;
    }

    static get styles() {
        return DashupComponent.css`
            :host {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                font-family: "fira-sans-2", Verdana, sans-serif;
                --height: 150px;
            }
            
            #q-graph {
                display: block;
                position: relative;
                width: 100%;
                height: var(--height); 
                background: transparent;
                font-size: 11px;
            }
            
            #q-graph caption {
                caption-side: top;
                left: 100%;
                width: 200%;
                text-transform: uppercase;
                top: calc(var(--height)*-0.15);
                position: relative;
                z-index: 10; 
                font-weight: bold;
            }
            
            #q-graph tr, #q-graph th, #q-graph td {
                position: absolute;
                bottom: 0;
                z-index: 2;
                padding: 0;
                text-align: center; 
            }
            
            #q-graph tr, #q-graph td {
                left: 40%;
            }
            
            #q-graph td {
                transition: all .3s ease;
                
                  &:hover {
                    background-color: desaturate(#85144b, 100);
                    opacity: .9;
                    color: white;
                  }
            }
            
            #q-graph td:hover {
                background-color: rgb(38, 166, 180);
                opacity: .9;
                color: white;
            }
            
            #q-graph tbody tr {
                height: var(--height); 
                border-right: 1px dotted #C4C4C4;
                border-bottom: 0px;
                color: #AAA;
            }
                        
            #q-graph tbody th {
                bottom: -1.75em;
                vertical-align: top;
                font-weight: normal;
                color: #333;
            }
            
            #q-graph .bar {
                width: 25%;
                border: 1px solid;
                border-bottom: none;
                color: #000;
            }
            
            #q-graph .bar p {
                margin: 0 0 0 0;
                padding: 0;
                opacity: .4;
            }  
            
            #ticks {
                position: relative;
                top: calc(var(--height)*-1.025); 
                left: 2px;
                width: 100%;
                height: var(--height); 
                z-index: 1;
                margin-bottom: calc(var(--height)*-1); 
                font-size: 10px;
                font-family: "fira-sans-2", Verdana, sans-serif;
            }
            
            #ticks .tick {
                position: relative;
                border-bottom: 1px dotted #C4C4C4;
                width: 95%;
            }
            
            #ticks .tick p {
                position: absolute;
                left: -5em;
                top: -0.8em;
                margin: 0 0 0 0.5em;
            }
        `;
    }

    static get properties() {
        return {
            title: {type: String},
            category: {type: String},
            data: {type: Array}
        };
    }

    constructor() {
        super();
        this.data = [];
    }

    handleData(data) {
        let values = Object.values(data.data);
        if (values.length > 0) {
            switch(data.mode){
                case MessageBroker.MessageMode.ADD:
                    let position = -1;
                    this.data.find((element, index) => {
                        if(element.category === values[1]){
                            position = index;
                            return true;
                        }
                        return false;
                    });
                    if(position >= 0){
                        this.data[position] = {"category": values[1], "value": Number(this.data[position].value) + Number(values[0])}
                    } else {
                        this.data.push({"category": values[1], "value": values[0]});
                    }
                     break;
                case MessageBroker.MessageMode.DELETE:
                    for(let element of values){
                        this.data.splice(this.data.indexOf({"category": element[1], "value": element[0]}), 1);
                    }
                    break;
            }
            this.requestUpdate();
        }
    }

    getValue(){
        return this.data;
    }

}

customElements.define("dashup-chart", DashupChart);