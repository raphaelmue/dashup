<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template>
    <h1>
        <img src="" />
        Weather in: <span id="city"> </span>
    </h1>
    <p>
        <span id="temperature"></span> <span id="weather_description"></span>
    </p>
</template>

<script>
    const template = document.currentScript.ownerDocument.querySelector('template').content;

    class WeatherComponent extends HTMLElement {

        constructor(){
            super();
        }

        connectedCallback() {
            this.attachShadow({mode: 'open'});
            this.shadowRoot.appendChild(document.importNode(template, true));
        }
    }

    customElements.define("dashup-weather", WeatherComponent);
</script>