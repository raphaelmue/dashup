import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module'

export class DashupShareBanner extends LitElement {

    render() {
        return html`
            <dashup-grid-layout>
                <dashup-text-input name="code"
                                   label='Share Code' 
                                   placeholder='Enter the share code here...' 
                                   value="${this.company}"
                                   layout='{"row": 1, "offset": 2, "size": 4}'> 
                </dashup-text-input>
                <dashup-button text="Send"
                               api="https://api.iextrading.com/1.0/stock/%code%/chart"
                               consumers="price development" 
                               producers="code"
                               layout='{"row": 1, "offset": 1, "size": 2}'>
                </dashup-button>
                <dashup-display name="development"
                                label="Development" 
                                quantity="%" 
                                path="22 changePercent"
                                layout='{"row": 2, "offset": 2, "size": 3}'>              
                </dashup-display>
                <dashup-display name="price" 
                                label="Share Value"
                                quantity="$" 
                                path="22 high"
                                layout='{"row": 2, "offset": 0, "size": 3}'>
                </dashup-display>
            </dashup-grid-layout>
        `;
    }

    static get properties() {
        return {
            company: {type: String, reflect: true},
        };
    }

    constructor() {
        super();
        this.company = "SAP";
    }

}

customElements.define("dashup-share-banner", DashupShareBanner);