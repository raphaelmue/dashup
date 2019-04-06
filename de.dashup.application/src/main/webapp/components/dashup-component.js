import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module';
export class DashupComponent extends LitElement {

    static get properties() {
        return {
            name: {type: String},
            layout: {type: Object}
        }
    }

    constructor() {
        super();
        new CSSStyleSheet().replace('@import url("https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css")').then(sheet => {
            this.shadowRoot.adoptedStyleSheets = [...document.adoptedStyleSheets, sheet];
        });
    }

}