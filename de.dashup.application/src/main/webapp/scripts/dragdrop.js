// dragula([document.querySelector('#drag_container_section')])
function load () {

    dragula([document.getElementById('drag_container_section')], {

        moves: function (el, container, handle) {
            return handle.classList.contains('handle');
        }
    }).on('drop', onDropSection);




    let i = 1;
    while (i <= 3) {
        var idName = "#drag_container" + i;
        dragula([document.querySelector(idName)]).on('drop', onDrop);
        i++;
    }
}

function onDrop (el, to, from) {
    var id= el.parentNode.id;
    console.log(getPostionOfPanels(id,"dashup_panel"));

}

function onDropSection (el, to, from) {
    var id= el.parentNode.id;
    console.log(getPostionOfPanels(id,"dashup_section"));
}

function onSubmit()
{
    console.log("Submit");

        $.post('../layoutmode/confirmChanges',   // url
            { myData: 'This is my data.' }, // data to be submit
            function(data, status, jqXHR) {// success callback
                console.log("Success");
            })
}

function getPostionOfPanels(sectionId,className)
{
    let positions = [];
    var panelPosition = [];
    let sectionTmp = document.getElementById(sectionId).getElementsByClassName(className);
    for (let i=0;i<sectionTmp.length;i++)
    {
        positions.push({
            key:   sectionTmp[i].id,
            value: i
        });
    }
    panelPosition.push(
        {
            key: sectionId,
            value: positions
        }
    )

    return panelPosition;
}

