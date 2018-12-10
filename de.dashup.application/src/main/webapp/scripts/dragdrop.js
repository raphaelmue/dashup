// dragula([document.querySelector('#drag_container_section')])
var layoutStorage = [];
var sectionOrder;
var sectionHeadings = [];
var sectionId = 0;
var sectionsToDelte =[];
function load () {

    dragula([document.getElementById('drag-container-section')], {

        moves: function (el, container, handle) {
            return handle.classList.contains('handle');
        }
    }).on('drop', onDropSection);

    let i = 1;
    while (i <= 3) {
        var idName = "#drag-container" + i;
        dragula([document.querySelector(idName)]).on('drop', onDrop);
        i++;
    }
}

function onDrop (el, to, from) {
    var id= el.parentNode.parentNode.id;
    var newPosition = getPositionOfPanels(id,"dashup-panel");
    for(var i=0;i<layoutStorage.length;i++){

        if(layoutStorage[i][0].key === newPosition[0].key)
        {
            layoutStorage.splice(i,1);
            break;
        }
    }

    layoutStorage.push(newPosition);



}

function onDropSection (el, to, from) {
    var id= el.parentNode.id;
    var newPosition = getPositionOfPanels(id,"dashup-section");
    sectionOrder = newPosition;
}

function onSubmit()
{
    $.ajax({
        type: "POST",
        url: "../layoutmode/handleLayout",
        data: JSON.stringify(formatChanges()),
        dataType: "json",
        contentType : "application/json"
    })

    layoutStorage = [];
    sectionOrder = null;
    sectionHeadings = [];
    sectionsToDelte =[];
}

function onDelete(sectionId)
{
    sectionsToDelte.push(sectionId);
    if(layoutStorage!=undefined)
    {
        for(var i=0;i<layoutStorage.length;i++){

            if(layoutStorage[i][0].key === sectionId)
            {
                layoutStorage.splice(i,1);
                break;
            }

        }
    }

    if(sectionOrder!=undefined)
    {
        for(var i=0;i<sectionOrder[0].value.length;i++){

            if(sectionOrder[0].value[i].key===sectionId){
                sectionOrder[0].value.splice(i,1);
            }
        }
    }


    if(sectionHeadings!=undefined){
        for(var i=0;i<sectionHeadings.length;i++){

            if(sectionHeadings[i].key === sectionId)
            {
                sectionHeadings.splice(i,1);
            }
        }
    }





    var element = document. getElementById(sectionId);
    element. parentNode. removeChild(element);
}

function onUndo() {

    location.reload();

}

function inputChanged(inputText,id)
{   var stringId = ""+id;
    var idType;
    if(stringId.includes("n"))idType="in";
    else{
        idType="i";
    }
    var inputField = document.getElementById(idType+stringId.replace("sn",""));
    inputField.setAttribute("value",inputText);
    var sid;
    var stringId = id+"";
    if(stringId.includes("s"))
    {
        sid = id;
    }
    else {
        sid = "s" + id;
    }
    for(var i=0;i<sectionHeadings.length;i++){

        if(sectionHeadings[i].key==id)
        {
            sectionHeadings.splice(i,1);
        }
    }

    sectionHeadings.push(
        {
            key:sid,
            value:inputText
        }

    )
}


function getPositionOfPanels(sectionId,className)
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
    var dashupStructure = {sections:[]};
    for(var i=0;i<layoutStorage.length;i++){

        var panelArray = [];

        for(var j=0;j<layoutStorage[i][0].value.length;j++){

            var id = layoutStorage[i][0].value[j].key;
            var order = layoutStorage[i][0].value[j].value;
            var panel = {id:id,panel_order:order};
            panelArray.push(panel);


        }

        var panelPosition = getSectionPosition(layoutStorage[0][0].key);

        dashupStructure.sections.push(
            {
                section_id : layoutStorage[0][0].key,
                panels: panelArray,
                section_name:getSectionName(layoutStorage[0][0].key),
                section_order:panelPosition
            }
        )
    }

    for(var i=0;i<sectionHeadings.length;i++){

        if(sectionHeadings[i].value != "%old%");
        var sectionOrderValue = -1;

        if(sectionOrder!=undefined)
        {
            if(sectionOrder!=undefined)
            {
                for(var j=0;j<sectionOrder[0].value.length;j++){

                    if(sectionOrder[0].value[j].key === sectionHeadings[i].key)
                    {
                        sectionOrderValue = sectionOrder[0].value[j].value;
                        sectionOrder[0].value[j].value = -1;

                    }

                }
            }

        }
        dashupStructure.sections.push(
            {
                section_id : sectionHeadings[i].key,
                panels: [],
                section_name:sectionHeadings[i].value,
                section_order:sectionOrderValue
            }
        )


    }

    if(sectionOrder!=undefined)
    {
        for(var i=0;i<sectionOrder[0].value.length;i++){

            if(sectionOrder[0].value[i].value != -1)
            {
                dashupStructure.sections.push(
                    {
                        section_id : sectionOrder[0].value[i].key,
                        panels: [],
                        section_name:"%old%",
                        section_order:sectionOrder[0].value[i].value
                    }
                )
            }

        }

    }

    if(sectionsToDelte!=undefined)
    {
        for(let i=0;i<sectionsToDelte.length;i++)
        {
            dashupStructure.sections.push(
                {
                    section_id : sectionsToDelte[i],
                    panels: [],
                    section_name:"",
                    section_order:-10
                }
            )
        }
    }
    return dashupStructure;
}

function getSectionPosition(sectionId)
{
    if(sectionOrder==undefined)return -1;
    for(var i=0;i<sectionOrder[0].value.length;i++){

        if(sectionId==sectionOrder[0].value[i].key)
        {
            return sectionOrder[0].value[i].value;
        }
    }

    return -1;
}

function getSectionName(sectionId)
{
    for(var i=0;i<sectionHeadings.length;i++){

        if(sectionId==sectionHeadings[i].key)
        {
            var ret = sectionHeadings[i].value;
            sectionHeadings[i].value = "%old%";
            return ret;
        }
    }

    return "%old%";
}

function addSection()
{
    var dragContainer = document.getElementById("drag-container-section");
    var newSection = document.createElement("div");
    newSection.setAttribute("class","dashup-section");
    newSection.setAttribute("id","sn" + sectionId);

    var newHeadingDiv = document.createElement("div");
    var handleButtonSpan = document.createElement("span");
    handleButtonSpan.setAttribute("class","handle");
    var handleButtonText = document.createTextNode("#");
    handleButtonSpan.appendChild(handleButtonText);
    handleButtonSpan.setAttribute("id","hn" + sectionId);
    newHeadingDiv.appendChild(handleButtonSpan);


    var inputButtonSpan = document.createElement("span");
    var inputField = document.createElement("input");
    inputField.setAttribute("type","text");
    inputField.setAttribute("name","txt");
    inputField.setAttribute("value","New Section");
    inputField.setAttribute("onchange","inputChanged(this.value,\"" + "sn" + sectionId + "\")");
    inputField.setAttribute("id","in" + sectionId);
    inputButtonSpan.appendChild(inputField);
    var deleteButton = document.createElement("button");
    deleteButton.setAttribute("type","button");
    deleteButton.setAttribute("onclick","onDelete('" + "sn" + sectionId + "')");
    deleteButton.setAttribute("class","btn btn-primary");
    deleteButton.setAttribute("id","bn" + sectionId);
    var xSign = document.createTextNode("x");
    deleteButton.appendChild(xSign);
    inputButtonSpan.appendChild(deleteButton);


    newHeadingDiv.appendChild(inputButtonSpan);
    newSection.appendChild(newHeadingDiv);

    var newhr = document.createElement("hr");
    newSection.appendChild(newhr);
    var dragContainerPanel = document.createElement("div");
    dragContainerPanel.setAttribute("class","drag-container");
    dragContainerPanel.setAttribute("id","drag-container0"+ sectionId);

    newSection.appendChild(dragContainerPanel);

    dragContainer.appendChild(newSection);

    sectionId++;

    var newPosition = getPositionOfPanels("drag-container-section","dashup-section");
    sectionOrder = newPosition;
}

