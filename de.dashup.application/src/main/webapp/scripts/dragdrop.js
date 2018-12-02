// dragula([document.querySelector('#drag_container_section')])
var layoutStorage = [];
var sectionOrder;
var sectoinHeadings = [];
var sectionId = 0;
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
    var id= el.parentNode.parentNode.id;
    var newPosition = getPostionOfPanels(id,"dashup_panel");
    for(var i=0;i<layoutStorage.length;i++){

        if(layoutStorage[i][0].key == newPosition[0].key)
        {
            layoutStorage.splice(i,1);
            break;
        }
        //console.log(layoutStorage[i][0].key);

    }
    console.log(layoutStorage);
    layoutStorage.push(newPosition);
    console.log(layoutStorage);


}

function onDropSection (el, to, from) {
    var id= el.parentNode.id;
    var newPosition = getPostionOfPanels(id,"dashup_section");
    sectionOrder = newPosition;
    console.log(sectionOrder);
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

function onDelete(sectionId)
{
    var element = document. getElementById(sectionId);
    element. parentNode. removeChild(element);
}

function inputChanged(inputText,id)
{
    for(var i=0;i<sectoinHeadings.length;i++){

        if(sectoinHeadings[i].key==id)
        {
            sectoinHeadings.splice(i,1);
        }
    }

    sectoinHeadings.push(
        {
            key:id,
            value:inputText
        }

    )
    console.log(sectoinHeadings);
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

function formatChanges()
{
    var dashupStructure;


}

function addSection()
{
    var dragContainer = document.getElementById("drag_container_section");
    var newSection = document.createElement("div");
    newSection.setAttribute("class","dashup_section");
    newSection.setAttribute("id","sn" + sectionId);

    var newHeadingDiv = document.createElement("div");
    var handleButtonSpan = document.createElement("span");
    handleButtonSpan.setAttribute("class","handle");
    var handleButtonText = document.createTextNode("#");
    handleButtonSpan.appendChild(handleButtonText);
    newHeadingDiv.appendChild(handleButtonSpan);


    var inputButtonSpan = document.createElement("span");
    var inputField = document.createElement("input");
    inputField.setAttribute("type","text");
    inputField.setAttribute("name","txt");
    inputField.setAttribute("value","New Section");
    inputField.setAttribute("onchange","inputChanged(this.value)");
    inputButtonSpan.appendChild(inputField);
    var deleteButton = document.createElement("button");
    deleteButton.setAttribute("type","button");
    deleteButton.setAttribute("onclick","onDelete('" + "sn" + sectionId + "')");
    deleteButton.setAttribute("class","btn btn-circle btn-lg");
    var xSign = document.createTextNode("x");
    deleteButton.appendChild(xSign);
    inputButtonSpan.appendChild(deleteButton);


    newHeadingDiv.appendChild(inputButtonSpan);
    newSection.appendChild(newHeadingDiv);

    var newhr = document.createElement("hr");
    newSection.appendChild(newhr);
    var dragContainer = document.createElement("div");
    dragContainer.setAttribute("drag_container");
    dragContainer.setAttribute("drag_container0"+ sectionId);

    newSection.appendChild(dragContainer);

    dragContainer.appendChild(newSection);

    sectionId++;
}

