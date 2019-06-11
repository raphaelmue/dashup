import {DashupComponent} from "./dashup-component.js";

export class DashupClock extends DashupComponent {

    render() {
        return DashupComponent.html`
            <style>
                #hours {
                     -webkit-transform: rotate(${30 * this.hours}deg);
                }
                #minutes {
                     -webkit-transform: rotate(${6 * this.minutes}deg);
                }
                #seconds {
                     -webkit-transform: rotate(${6 * this.seconds}deg);  
                }
             </style>
                    
            <div class="clock">
                  <div id="hours"></div>
                  <div id="minutes"></div>
                  <div id="seconds"></div>
                  <div class="three"></div>
                  <div class="six"></div>
                  <div class="nine"></div>
                  <div class="twelve"></div>
                  <div class="center"></div>
            </div>
        `;
    }

    static get styles() {
        return DashupComponent.css`
            :host {
                margin: 0;
                padding: 0;
                border: 0;
            }
            
            .clock {
                height: 150px;
                width: 150px;
                background-color: var(--color-background);
                border-radius: 100%;
                border: 4.5px solid var(--color-primary-dark);
                margin: auto;
                position: absolute;
                top: 0;
                left: 0;
                bottom: 0;
                right: 0;
            }
            
            .twelve {
                position: absolute;
                width: 4.5px;
                height: 22.5px;
                background: var(--color-primary-light);
                left: 0;
                top: 0;
                right: 0;
                margin: 0 auto;
            }
            
            .three {
                position: absolute;
                width: 22.5px;
                height: 4.5px;
                background: var(--color-primary-light);
                top: 0;
                bottom: 0;
                right: 0;
                margin: auto 0;
            }
            
            .six {
                position: absolute;
                width: 4.5px;
                height: 22.5px;
                background: var(--color-primary-light);
                left: 0;
                bottom: 0;
                right: 0;
                margin: 0 auto;
            }
            
            .nine {
                position: absolute;
                width: 22.5px;
                height: 4.5px;
                background: var(--color-primary-light);
                top: 0;
                bottom: 0;
                left: 0;
                margin: auto 0;
            }
            
            .center {
                width: 15px;
                height: 15px;
                position: absolute;
                left: 0;
                right: 0;
                bottom: 0;
                top: 0;
                margin: auto;
                background: var(--color-primary-light);
                border-radius: 100%;
            }
            
            #seconds {
                position: absolute;
                height: 48px;
                width: 3px;
                left: 0;
                right: 0;
                bottom: 48px;
                top: 0px;
                margin: auto;
                background: #ff4136;
                -webkit-transform-origin: bottom;
            }
            
            #minutes {
                position: absolute;
                height: 75px;
                width: 3px;
                left: 0;
                right: 0;
                bottom: 75px;
                top: 0px;
                margin: auto;
                background: var(--color-primary);
                -webkit-transform-origin: bottom;
            }
            
            #hours {
                position: absolute;
                height: 37.5px;
                width: 6px;
                left: 0;
                right: 0;
                bottom: 37.5px;
                top: 0px;
                margin: auto;
                background: var(--color-primary);
                -webkit-transform-origin: bottom;
            }
        `;
    }

    static get properties() {
        return {
            hours: {type: Number, reflect: true, attribute: false},
            minutes: {type: Number, reflect: true, attribute: false},
            seconds: {type: Number, reflect: true, attribute: false}
        };
    }

    constructor() {
        super();
        window.setInterval(() => {
            this.date = new Date();
            this.hours = this.date.getHours() % 12;
            this.minutes = this.date.getMinutes();
            this.seconds = this.date.getSeconds();
            this.requestUpdate();
        }, 1000);
    }

}
customElements.define("dashup-clock", DashupClock);