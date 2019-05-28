import {LitElement, html, css} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
import {styleMap} from "https://unpkg.com/lit-html@1.1.0/directives/style-map.js?module";
import {classMap} from "https://unpkg.com/lit-html@1.1.0/directives/class-map.js?module";

export class DashupComponent extends LitElement {

    static html = html;
    static css = css;
    static styleMap = styleMap;
    static classMap = classMap;

    static get properties() {
        return {
            name: {type: String},
            layout: {type: Object}
        };
    }

    constructor() {
        super();
        new CSSStyleSheet().replace("@import url(../styles/theme.style.css)").then((sheet) => {
            this.shadowRoot.adoptedStyleSheets = [...this.shadowRoot.adoptedStyleSheets, sheet];
        });
        new CSSStyleSheet().replace("@import url('https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css')").then((sheet) => {
            this.shadowRoot.adoptedStyleSheets = [...this.shadowRoot.adoptedStyleSheets, sheet];
        });

    }

}