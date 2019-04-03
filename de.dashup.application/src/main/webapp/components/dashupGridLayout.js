const dashupGridLayout = document.currentScript.ownerDocument.querySelector("#dashup-grid-layout-template").content;

class GridLayoutComponent extends LayoutComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupGridLayout, true);
        this.shadowRoot.appendChild(this.content);

        this.gridLayout = this.shadowRoot.querySelector("#dashup-grid-layout");
    }

    /**
     * Returns all attributes, that this web component can have.
     * @returns {string[]} array of attribute names
     */
    static get observedAttributes() {
        return ["col-size"];
    }

    /**
     * Called when web component is shown on screen. Used for registering events.
     **/
    connectedCallback() {
       super.connectedCallback();
    }

    attachContent(mutation, index) {
        let col = document.createElement("div");
        col.setAttribute("class", "col s" + this._getColSizeByIndex(index));
        col.appendChild(mutation.addedNodes[0]);
        this.gridLayout.appendChild(col);
    }

    _getColSizeByIndex(index) {
        let colSize = this.getAttribute("col-size").split(" ");
        return colSize[index % colSize.length];
    }

    getShadowRoot() {
        return this.shadowRoot;
    }

    get colSize() {
        return this.getAttribute("col-size");
    }

    set colSize(val) {
        this.setAttribute("col-size", val);
    }
}

customElements.define("dashup-grid-layout", GridLayoutComponent);