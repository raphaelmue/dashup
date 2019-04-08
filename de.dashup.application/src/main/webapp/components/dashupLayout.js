class LayoutComponent extends DashupComponent {

    constructor() {
        super();
    }

    /**
     * Called when web component is shown on screen. Used for iterating over all children elements.
     **/
    connectedCallback() {
        let index = 0;
        let ids = [];
        let observer = new MutationObserver((function (mutations) {
            if (mutations.length > 0) {
                mutations.forEach((function (mutation) {
                    if (mutation.addedNodes.length && mutation.addedNodes[0].nodeType === 1 &&
                        (!ids.includes(mutation.addedNodes[0].getAttribute("id")) || mutation.addedNodes[0].getAttribute("id") == null)) {
                        this.attachContent(mutation, index);

                        ids.push(mutation.addedNodes[0].getAttribute("id"));
                        index++;
                    }
                }).bind(this));
            }
        }).bind(this));

        observer.observe(this, {childList: true});
    }

    /**
     * To be implemented
     * @param mutation mutation to be added as a child
     */
    attachContent(mutation) {
    }
}