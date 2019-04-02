(function() {
    dragula([document.querySelector('.drag-drop-container')], {
        moves: function(el, container, handle) {
            return !handle.classList.contains('bloc--inner');
        }
    });

    dragula([].slice.apply(document.querySelectorAll('.bloc')), {
        direction: 'horizontal'
    });
})();

