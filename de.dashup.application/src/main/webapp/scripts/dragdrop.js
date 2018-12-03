// dragula([document.querySelector('#drag_container_section')])
var layoutStorage = [];
var sectionOrder;
var sectionHeadings = [];
var sectionId = 0;
var sectionsToDelte =[];
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
    var newPosition = getPositionOfPanels(id,"dashup_panel");
    for(var i=0;i<layoutStorage.length;i++){

        if(layoutStorage[i][0].key === newPosition[0].key)
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
    console.log(id);
    var newPosition = getPositionOfPanels(id,"dashup_section");
    sectionOrder = newPosition;
    console.log(sectionOrder);
}

function onSubmit()
{
    console.log("Submit");
    console.log(formatChanges());

    var data = {};
    data["test"] = "test";
    console.log(data);

        $.post('../layoutmode/confirmChanges',   // url
            {  headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: 'json',
                myData: JSON.stringify(data) }, // data to be submit
            function(data, status, jqXHR) {// success callback
                console.log("Success");
            })

    layoutStorage = [];
    sectionOrder = null;
    sectionHeadings = [];
    sectionsToDelte =[];
}

function onDelete(sectionId)
{
    sectionsToDelte.push(sectionId);
    console.log(sectionsToDelte);
    if(layoutStorage!=undefined)
    {
        for(var i=0;i<layoutStorage.length;i++){

            if(layoutStorage[i][0].key === sectionId)
            {
                layoutStorage.splice(i,1);
                break;
            }
            //console.log(layoutStorage[i][0].key);
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

function inputChanged(inputText,id)
{   var sid;
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
    console.log(sectionHeadings);
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
        var sectionOrderValue = "%old%";

        if(sectionOrder!=undefined)
        {
            if(sectionOrder!=undefined)
            {
                for(var j=0;j<sectionOrder[0].value.length;j++){

                    if(sectionOrder[0].value[j].key === sectionHeadings[i].key)
                    {
                        sectionOrderValue = sectionOrder[0].value[j].value;
                        sectionOrder[0].value[j].value = "%old%";

                    }

                }
            }

        }
        dashupStructure.sections.push(
            {
                section_id : sectionHeadings[i].key,
                panels: "",
                section_name:sectionHeadings[i].value,
                section_order:sectionOrderValue
            }
        )


    }

    if(sectionOrder!=undefined)
    {
        for(var i=0;i<sectionOrder[0].value.length;i++){

            if(sectionOrder[0].value[i].value != "%old%")
            {
                dashupStructure.sections.push(
                    {
                        section_id : sectionOrder[0].value[i].key,
                        panels: "",
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
            console.log(sectionsToDelte[i]);
            dashupStructure.sections.push(
                {
                    section_id : sectionsToDelte[i],
                    panels: "",
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
    if(sectionOrder==undefined)return "%old%";
    for(var i=0;i<sectionOrder[0].value.length;i++){

        if(sectionId==sectionOrder[0].value[i].key)
        {
            return sectionOrder[0].value[i].value;
        }
    }

    return "%old%";
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
    inputField.setAttribute("onchange","inputChanged(this.value,\"" + "sn" + sectionId + "\")");
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
    var dragContainerPanel = document.createElement("div");
    dragContainerPanel.setAttribute("class","drag_container");
    dragContainerPanel.setAttribute("id","drag_container0"+ sectionId);

    newSection.appendChild(dragContainerPanel);

    dragContainer.appendChild(newSection);

    sectionId++;

    var newPosition = getPositionOfPanels("drag_container_section","dashup_section");
    sectionOrder = newPosition;
}
