import {html, css} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {styleMap} from "https://unpkg.com/lit-html@1.0.0/directives/style-map.js?module";
import {DashupComponent} from "./dashup-component.js";
export class DashupChart extends DashupComponent{

    render() {

        let maxValue = this.data.sort((element1, element2) => { return element2.value - element1.value})[0].value;
        let heightMap = new Map();
        this.data.forEach((element) => {
           heightMap.set(element.category, {height: (element.value/maxValue)*100 + "%"})
        });
        //can be enhanced with multiple bars per category later on
        return html`
            <style>
               ${this.data.map((element, index) => {
                    return html`#q-graph #${element.category} {left: ${index * (80/this.data.length)}%;}`
               })}
               
               ${html`#q-graph tr, #q-graph th, #q-graph td {width: ${80/this.data.length}%}`}
            </style>

            <table id="q-graph">
                <caption>${this.title}</caption>
                <thead>
                    <tr>
                        <th class="category">${this.category}</th>
                    </tr>
                </thead>
                <tbody>
                    ${this.data.map(element => html`
                        <tr class="qtr" id="${element.category}">
                        <th scope="row">${element.category}</th>
                        <td class="category bar" style="${styleMap(heightMap.get(element.category))}"><p>${element.value}</p></td>
                        </tr>
                    `)}
                </tbody>
            </table>
            <div id="ticks">
                ${(() => {
                    let ticks = [];
                    for(let i = 1; i <= 5; i++){
                        ticks.push(html`<div class="tick" style="height: 20%;"><p>${Math.ceil(maxValue/100)*100/5*i}</p></div>`);
                    }
                    return ticks.reverse();
                })()}
            </div>
        `;
    }

    static get styles() {
        return css`
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
                width: 200%;
                text-transform: uppercase;
                top: calc(var(--height)*-0.3);
                position: relative;
                z-index: 10; 
                font-weight: bold;
            }
            
            #q-graph tr, #q-graph th, #q-graph td {
                position: absolute;
                bottom: 0;
                left: 40%;
                z-index: 2;
                padding: 0;
                text-align: center; 
            }
            
            #q-graph td {
                transition: all .3s ease;
            }
            
            #q-graph td:hover {
                background-color: rgb(38, 166, 180);
                opacity: .9;
                color: white;
            }
            
            #q-graph thead tr {
                width: 0;
                left: 100%;
                top: 50%;
                bottom: auto;
            }
            
            #q-graph thead th {
                width: 7.5em;
                height: auto ;
                padding: 0.5em 1em;
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
            
            #q-graph .category {
                background-color: rgb(38, 166, 154);
                border-color: transparent;
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

}
customElements.define("dashup-chart", DashupChart);