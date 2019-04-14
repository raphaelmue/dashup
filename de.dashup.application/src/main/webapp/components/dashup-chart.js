import {LitElement, html, css, unsafeCSS} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
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
                    return html`#q-graph #${element.category} {left: ${index * 150}px;}`})}
            </style>

            <table id="q-graph">
            <caption>${this.title}</caption>
            <thead>
            <tr>
            <th></th>
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
            body, html {
                height: 100%;
            }
            
            body {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                font-family: "fira-sans-2", Verdana, sans-serif;
            }
            
            #q-graph {
                display: block;
                position: relative;
                width: 600px;
                height: 300px;
                margin: 1.1em 0 0;
                padding: 0;
                background: transparent;
                font-size: 11px;
            }
            
            #q-graph caption {
                caption-side: top;
                width: 600px;
                text-transform: uppercase;
                letter-spacing: .5px;
                top: -40px;
                position: relative;
                z-index: 10; 
                font-weight: bold;
            }
            
            #q-graph tr, #q-graph th, #q-graph td {
                position: absolute;
                bottom: 0;
                width: 150px;
                z-index: 2;
                margin: 0;
                padding: 0;
                text-align: center; 
            }
            
            #q-graph td {
                transition: all .3s ease;
            }
            
            #q-graph td:hover {
                background-color: #4d4d4d;
                opacity: .9;
                color: white;
            }
            
            #q-graph thead tr {
                left: 100%;
                top: 50%;
                bottom: auto;
                margin: -2.5em 0 0 5em;
            }
            
            #q-graph thead th {
                width: 7.5em;
                height: auto ;
                padding: 0.5em 1em;
            }
            
            #q-graph thead th.sent {
                top: 0;
                left: 0;
                line-height: 2;
            }
            
            #q-graph thead th.paid {
                top: 2.75em;
                line-height: 2;
                left: 0;
            }
            
            #q-graph tbody tr {
                height: 296px;
                padding-top: 2px;
                border-right: 1px dotted #C4C4C4;
                color: #AAA;
            }
                        
            #q-graph tbody th {
                bottom: -1.75em;
                vertical-align: top;
                font-weight: normal;
                color: #333;
            }
            
            #q-graph .bar {
                width: 60px;
                border: 1px solid;
                border-bottom: none;
                color: #000;
            }
            
            #q-graph .bar p {
                margin: 5px 0 0;
                padding: 0;
                opacity: .4;
            }
            
            #q-graph .category {
                left: 45px;
                background-color: #39cccc;
                border-color: transparent;
            }
            
            #ticks {
                position: relative;
                top: -305px;
                left: 2px;
                width: 596px;
                height: 300px;
                z-index: 1;
                margin-bottom: -300px;
                font-size: 10px;
                font-family: "fira-sans-2", Verdana, sans-serif;
            }
            
            #ticks .tick {
                position: relative;
                border-bottom: 1px dotted #C4C4C4;
                width: 600px;
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