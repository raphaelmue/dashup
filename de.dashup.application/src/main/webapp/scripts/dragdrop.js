// dragula([document.querySelector('#drag_container_section')])
function load () {
    buildBoard();
    dragula([document.getElementById('drag_container_section')], {

        moves: function (el, container, handle) {
            return handle.classList.contains('handle');
        }
    });
    let i = 1;
    while (i <= 3) {
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

function buildBoard()
{
    //create section
    var newSection = document.createElement("div");
    newSection.setAttribute('class','section');

    //create section heading
    var newHeadingContainer = document.createElement("div");
    var newHandleButton = document.createElement("span");
    newHandleButton.setAttribute('class','handle');
    var newBtnText= document.createTextNode("#");
    newHandleButton.appendChild(newBtnText);
    var newHeadingSpan = document.createElement("span");
    var newHeadingElement = document.createElement("h3");
    var newHeading = document.createTextNode("Section 3");
    newHeadingElement.appendChild(newHeading);
    newHeadingSpan.appendChild(newHeadingElement);
    newHeadingContainer.appendChild(newHandleButton);
    newHeadingContainer.appendChild(newHeadingSpan);
    var newLine = document.createElement("hr");
    newHeadingContainer.appendChild(newLine);
    newSection.appendChild(newHeadingContainer);

    //create panels
    var newPanelContainer = document.createElement("div");
    newPanelContainer.setAttribute('class','drag_container');
    newPanelContainer.setAttribute('id','drag_container3');

    newPanelContainer.appendChild(createPanel("p1"));
    newPanelContainer.appendChild(createPanel("p2"));


    newSection.appendChild(newPanelContainer);

    var sectionDragContainer = document.getElementById("drag_container_section");
    sectionDragContainer.appendChild(newSection);


}

function createPanel(pId)
{
    var newPanel = document.createElement("div");
    newPanel.setAttribute('class','panel');
    newPanel.setAttribute('id',pId)

    return newPanel;

}

