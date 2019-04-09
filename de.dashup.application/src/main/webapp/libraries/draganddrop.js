let layout = [];
let selectedPanel;

(function() {
    dragula([document.querySelector('.drag-drop-container')], {
        moves: function(el, container, handle) {

            let draggedClass = handle.classList[0];
            console.log(handle);
            console.log(draggedClass);
            if(draggedClass === 'drag-drop-btn')
            {
                return !handle.classList.contains('bloc--inner');
            }
        }
    });

    dragula([].slice.apply(document.querySelectorAll('.bloc')), {
        direction: 'horizontal'
    });

    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.dropdown-trigger');
        var instances = M.Dropdown.init(elems, null);


    });


    $(".section-minus").on("click", function (event) {
        let sectionToDelete = event.currentTarget.parentNode.parentNode.parentNode;


        addSectionToDeleteToList(sectionToDelete);

        while(sectionToDelete.firstChild){
            sectionToDelete.removeChild(sectionToDelete.firstChild);
        }

        let sectionParent = sectionToDelete.parentNode;
        console.log(sectionToDelete);
        console.log(sectionParent);
        sectionParent.removeChild(sectionToDelete);



    });

    $(".dropdown-trigger").on("click", function (event) {
        let panel = event.currentTarget.parentNode.parentNode.parentNode;
        selectedPanel = panel.id;
        console.log(panel);
        console.log("panel id: " + selectedPanel);
    });

    $("#delete").on("click", function () {
        console.log("delete pressed");
        let panelToDelete = document.getElementById(selectedPanel);
        let panelToDeleteParent = panelToDelete.parentNode;

        console.log(panelToDelete);

        panelToDeleteParent.removeChild(panelToDelete);

        console.log("to delete: " + selectedPanel);



    });


})();

function addSectionToDeleteToList(sectionToDelete)
{
    let section = sectionToDelete.childNodes[1];
    let panels = section.childNodes;

    let panelStructure;
    panelStructure = makePanelStructure(panels,true);

    let sectionObject = {
        sectionName : "",
        sectionId : sectionToDelete.id,
        panelStructure: panelStructure,
        remove: true
    };

    layout.push(sectionObject);
}


function saveChanges()
{

    let dragDropContainer = document.getElementById('drag-drop-container');

    let sections = dragDropContainer.getElementsByClassName('wrapper');
    console.log(sections);

    let sectionsCount = sections.length;

    for (let i = 0; i < sectionsCount; i++)
    {
        let sectionName = sections[i]['children'][0]['innerText'];
        console.log(sectionName);
        let sectionId = sections[i].id;

        let panels = sections[i]['children'][1]['children'];

        let panelStructure = makePanelStructure(panels,false);

        let sectionObject = {
            sectionName : sectionName,
            sectionId : sectionId,
            panelStructure: panelStructure,
            remove: false
        };

        layout.push(sectionObject);




            }
    console.log(layout);
}

function makePanelStructure(panels, remove) {
    let panelsCount = panels.length;

    let panelStructure = [];

    for (let j = 0; j < panelsCount; j++)
    {
        console.log(panelSize);

        let panel = {
            panelId: panels[j].id,
            panelSize: panels[j]['attributes']['size'].value,
            remove: remove
        };
        panelStructure.push(panel);
    }

    return panelStructure;

}

