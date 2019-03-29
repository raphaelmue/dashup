class DashupComponent extends HTMLElement {

    escapeHTML(string) {
        let htmlEscapes = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#x27;',
            '/': '&#x2F;'
        };

        // Rege/x containing the keys listed immediately above.
        let htmlEscapeRegex = /[&<>"'\/]/g;

        // Escape a string for HTML interpolation.
        return ('' + string).replace(htmlEscapeRegex, function (match) {
            return htmlEscapes[match];
        });
    }

}