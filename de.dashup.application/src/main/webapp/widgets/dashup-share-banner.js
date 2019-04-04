import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module'
export class DashupShareBanner extends LitElement{

    render() {
        return html`
            <dashup-grid-layout>
                <dashup-text-input label='Share Code' 
                                   placeholder='Enter the share code here...' 
                                   layout='{"row": 1, "offset": 2, "size": 4}'> 
                </dashup-text-input>
                <dashup-button api='{"address": "https://api.iextrading.com/1.0/stock/${this.company}/chart"}' 
                               consumers="price development" 
                               layout='{"row": 1, "offset": 1, "size": 2}'>
                </dashup-button>
                <dashup-display name="price" 
                                path="22 changePercent"
                                layout='{"row": 2, "offset": 2, "size": 3}'>              
                </dashup-display>
                <dashup-display name="development" 
                                path="22 high"
                                layout='{"row": 2, "offset": 1, "size": 3}'>
                </dashup-display>
            </dashup-grid-layout>
        `;
    }

    static get properties() {
        return {
            company: {type: String, reflect: true},
            development: {type: String, attribute: false},
            price: {type: String, attribute: false}
        };
    }

    constructor() {
        super();
        this.company= "SAP";
    }

}
customElements.define("dashup-share-banner",DashupShareBanner);