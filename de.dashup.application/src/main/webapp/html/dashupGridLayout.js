const dashupGridLayout = document.currentScript.ownerDocument.querySelector('#dashupGridLayout').content;

class GridLayoutComponent extends HTMLElement {

    constructor() {
        super();
        this.attachShadow({mode: 'open'});
        this.content = document.importNode(dashupGridLayout, true);
        this.shadowRoot.appendChild(this.content);
    }

    static get observedAttributes() {
        return ['action', 'api', 'param', 'consumers'];
    }

    connectedCallback() {

    }

    disconnectedCallback() {
        //NOOP
    }

    adoptedCallback() {
        //NOOP
    }

    attributeChangedCallback(name, oldValue, newValue) {
        //NOOP
    }

    get name() {
        return this.getAttribute('name');
    }

    set name(val) {
        this.setAttribute('name', val);
    }

    get rows() {
        return this.getAttribute('rows');
    }

    set rows(val) {
        this.setAttribute('rows', val);
    }

    get columns() {
        return this.getAttribute('columns');
    }

    set columns(val) {
        this.setAttribute('columns', val);
    }
}

customElements.define("dashup-grid-layout", GridLayoutComponent);