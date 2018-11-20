<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template>

    <style>
        #change {
            -moz-box-shadow: 0px 1px 0px 0px #f0f7fa;
            -webkit-box-shadow: 0px 1px 0px 0px #f0f7fa;
            box-shadow: 0px 1px 0px 0px #f0f7fa;
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #33bdef), color-stop(1, #019ad2));
            background:-moz-linear-gradient(top, #33bdef 5%, #019ad2 100%);
            background:-webkit-linear-gradient(top, #33bdef 5%, #019ad2 100%);
            background:-o-linear-gradient(top, #33bdef 5%, #019ad2 100%);
            background:-ms-linear-gradient(top, #33bdef 5%, #019ad2 100%);
            background:linear-gradient(to bottom, #33bdef 5%, #019ad2 100%);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#33bdef', endColorstr='#019ad2',GradientType=0);
            background-color:#33bdef;
            -moz-border-radius:6px;
            -webkit-border-radius:6px;
            border-radius:6px;
            border:1px solid #057fd0;
            display:inline-block;
            cursor:pointer;
            color:#ffffff;
            font-family:Arial;
            font-size:14px;
            font-weight:bold;
            padding:6px 24px;
            text-decoration:none;
            text-shadow:0px -1px 0px #5b6178;
        }
        #change:hover {
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #019ad2), color-stop(1, #33bdef));
            background:-moz-linear-gradient(top, #019ad2 5%, #33bdef 100%);
            background:-webkit-linear-gradient(top, #019ad2 5%, #33bdef 100%);
            background:-o-linear-gradient(top, #019ad2 5%, #33bdef 100%);
            background:-ms-linear-gradient(top, #019ad2 5%, #33bdef 100%);
            background:linear-gradient(to bottom, #019ad2 5%, #33bdef 100%);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#019ad2', endColorstr='#33bdef',GradientType=0);
            background-color:#019ad2;
        }
        #change:active {
            position:relative;
            top:1px;
        }
    </style>

    <div style="border-style: groove; padding: 15px">
        <h1>
            <img id="icon" src=""/>
            Weather in <span id="city"> </span>
            <a id="change" href="#" style="float: right">change city</a>
        </h1>
        <p>
            <span><strong>Temperature:   </strong><span id="temperature"></span></span>
            <span style="float: right"><strong>Forecast:   </strong><span id="weather_description"></span></span>
        </p>
    </div>
</template>

<script>
    const template = document.currentScript.ownerDocument.querySelector('template').content;

    class WeatherComponent extends HTMLElement {

        constructor(){
            super();
            this.attachShadow({mode: 'open'});
            this.content = document.importNode(template, true);
            this.shadowRoot.appendChild(this.content);

            this.displayedIcon = this.shadowRoot.querySelector('#icon');
            this.displayedCity = this.shadowRoot.querySelector('#city');
            this.displayedTemp = this.shadowRoot.querySelector('#temperature');
            this.displayedDesc = this.shadowRoot.querySelector('#weather_description');
            this.button        = this.shadowRoot.querySelector('#change');
            this.button.addEventListener('click', () => {
                this.city = prompt("Which city do you prefer", "");
            });
        }

        update(obj){
            fetch("http://api.openweathermap.org/data/2.5/weather?q=" + this.city + "&units=metric&appid=524da7907b1939626510c547ae65d67c")
                .then(function(response) {
                    return response.json();
                })
                .then(function(response) {
                    obj.displayedCity.innerText = obj.city;
                    obj.displayedIcon.src = "https://openweathermap.org/img/w/" + response.weather[0].icon + ".png";
                    obj.displayedTemp.innerText = response.main.temp + "°C";
                    obj.displayedDesc.innerText = response.weather[0].description;
                });
        }

        static get observedAttributes() {
            return ['city'];
        }

        connectedCallback() {
            if (!this.hasAttribute('city')) {
                this.setAttribute('city', 'Karlsruhe');
            }
            this.update(this);
        }

        attributeChangedCallback(name, oldValue, newValue) {
            this.update(this);
        }

        disconnectedCallback(){
            //Noop
        }

        get city(){
            return this.getAttribute('city');
        }

        set city(val){
            this.setAttribute('city', val);
        }
    }

    customElements.define("dashup-weather", WeatherComponent);
</script>