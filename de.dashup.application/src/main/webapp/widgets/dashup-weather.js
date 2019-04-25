import {LitElement, html} from "https://unpkg.com/lit-element@2.1.0/lit-element.js?module";
export class DashupWeather extends LitElement{

    render() {
        return html`
            <dashup-grid-layout>
                <dashup-text-input name="location"
                                   placeholder='Enter the location here...' 
                                   value="${this.location}"
                                   layout='{"row": 1, "offset": 0, "size": 8}'> 
                </dashup-text-input>
                <dashup-button text="Send"
                               mode="display"
                               api="http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%"
                               params="location"
                               consumers='{"temperature": "apiData main temp", "humidity": "apiData main humidity"}' 
                               layout='{"row": 1, "offset": 1, "size": 3}'>
                </dashup-button>
                <dashup-display name="temperature"
                                label="Temperature" 
                                quantity="°C" 
                                layout='{"row": 2, "offset": 0, "size": 12}'>              
                </dashup-display>
                <dashup-display name="humidity" 
                                label="Humidity"
                                quantity="%" 
                                layout='{"row": 3, "offset": 0, "size": 12}'>
                </dashup-display>
            </dashup-grid-layout>
        `;
    }

    static get properties() {
        return {
            location: {type: String, reflect: true},
        };
    }

    constructor() {
        super();
        this.location= "Karlsruhe";
    }

}
customElements.define("dashup-weather", DashupWeather);