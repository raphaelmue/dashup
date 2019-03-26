const dashupInput = document.currentScript.ownerDocument.querySelector('#dashup-input-template').content;

class InputComponent extends HTMLElement {

    constructor() {
        super();
        this.attachShadow({mode: 'open'});
        this.content = document.importNode(dashupInput, true);
        this.shadowRoot.appendChild(this.content);

        this.input = this.shadowRoot.querySelector('#dashup-input');
        this.input.value = "Heidelberg";
        this.inputLabel = this.shadowRoot.querySelector('#dashup-input-label');
        this.text = this.input.value;
    }

    _callAPI() {
        let url;
        let that = this;
        switch (this.action) {
            case 'rfc':
                url = this._handleParams();
                fetch(url).then(function (response) {
                    that._checkResponse(response.status);
                });
                break;
            case 'data':
                url = this._handleParams();
                fetch(url).then(function (response) {
                    that._checkResponse(response.status);
                    return response.json();
                })
                    .then(function (response) {
                        let display = that.getAttribute('consumers').split(" ");
                        display.forEach(val => {
                            let obj = document.querySelector("[name='" + val + "']")
                            obj.handleData(response, val);
                        })
                    });
                break;
        }
    }

    _handleParams() {
        let url;
        if (this.hasAttribute('param')) {
            url = this.api.includes('?') ? this.api + "&" : this.api + "?";
            url += this.getAttribute('param') + "=" + this.text;
        } else {
            url = this.api;
        }
        return url;
    }

    _validURL(str) {
        var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
        return regexp.test(str);
    }

    _checkResponse(status) {
        if (status = 200) {
            this.input.style.backgroundColor = "#89f442";
            setTimeout(() => {
                this.input.style.backgroundColor = "";
            }, 500);
        } else {
            this.input.style.backgroundColor = "red";
            setTimeout(() => {
                this.input.style.backgroundColor = "";
            }, 500);
        }
    }

    static get observedAttributes() {
        return ['name', 'label', 'action', 'api', 'param', 'consumers'];
    }

    connectedCallback() {
        this.inputLabel.innerHTML = this.getAttribute("label");
        if (this.hasAttribute('api') && this._validURL(this.api)) {
            this.input.addEventListener('change', () => {
                this.text = this.input.value;
                this._callAPI();
            });
            if (!this.hasAttribute('action')) {
                this.setAttribute('action', 'rpc');
            }
        }
    }

    disconnectedCallback() {
        //NOOP
    }

    adoptedCallback() {
        //NOOP
    }

    attributeChangedCallback(name, oldValue, newValue) {
        this._callAPI();
    }

    get name() {
        return this.getAttribute('name');
    }

    set name(val) {
        this.setAttribute('name', val);
    }

    get label() {
        return this.getAttribute('label');
    }

    set label(val) {
        this.setAttribute('label', val);
    }

    get formGroup() {
        return this.getAttribute('group');
    }

    set formGroup(val) {
        this.setAttribute('group', val);
    }

    get action() {
        return this.getAttribute('action');
    }

    set action(val) {
        this.setAttribute('action', val);
    }

    get api() {
        return this.getAttribute('api');
    }

    set api(val) {
        this.setAttribute('api', val);
    }

    get param() {
        return this.getAttribute('param');
    }

    set param(val) {
        this.setAttribute('param', val);
    }

    get consumers() {
        return this.getAttribute('consumers');
    }

    set consumers(val) {
        this.setAttribute('consumers', val);
    }

}

customElements.define("dashup-input", InputComponent);