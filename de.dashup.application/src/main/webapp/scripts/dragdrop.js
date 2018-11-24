// dragula([document.querySelector('#drag_container_section')])
function load () {
    dragula([document.getElementById('drag_container_section')], {

        moves: function (el, container, handle) {
            return handle.classList.contains('handle');
        }
    });
    let i = 1;
    while (i <= 2) {
        var idName = "#drag_container" + i;
        dragula([document.querySelector(idName)]).on('drop', onDrop);
        i++;
    }

}

function onDrop (el, to, from) {
    var id= el.id;
    var pos = document.getElementById(id).offsetLeft;
    console.log(pos);
    console.log(to);
    console.log(from);
}



