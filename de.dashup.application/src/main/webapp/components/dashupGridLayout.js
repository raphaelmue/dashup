const dashupGridLayout = document.currentScript.ownerDocument.querySelector("#dashup-grid-layout-template").content;

class GridLayoutComponent extends DashupComponent {

    constructor() {
        super();
        this.attachShadow({mode: "open"});
        this.content = document.importNode(dashupGridLayout, true);
        this.shadowRoot.appendChild(this.content);

        this.gridLayout = this.shadowRoot.querySelector("#dashup-grid-layout");
    }

    static get observedAttributes() {
        return ["col-size"];
    }

    connectedCallback() {
        let that = this;
        let index = 0;
        let ids = [];
        let observer = new MutationObserver(function (mutations) {
            if (mutations.length > 0) {
                mutations.forEach(function (mutation) {
                    if (mutation.addedNodes.length && mutation.addedNodes[0].nodeType === 1 &&
                        (!ids.includes(mutation.addedNodes[0].getAttribute("id")) || mutation.addedNodes[0].getAttribute("id") == null)) {
                        let col = document.createElement("div");
                        col.setAttribute("class", "col s" + that._getColSizeByIndex(index));
                        col.appendChild(mutation.addedNodes[0]);
                        that.gridLayout.appendChild(col);
                        ids.push(mutation.addedNodes[0].getAttribute("id"));
                        index++;
                    }
                });
            }
        });

        observer.observe(this, {childList: true});
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