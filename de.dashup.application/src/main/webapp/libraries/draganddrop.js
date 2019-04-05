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


    $(".section-minus").on("click", function (event) {
        let sectionToDelete = event.currentTarget.parentNode.parentNode.parentNode;
        let sectionId = sectionToDelete.id;

        while(sectionToDelete.firstChild){
            sectionToDelete.removeChild(sectionToDelete.firstChild);
        }

        addSectionToDeleteToList(sectionId);

    });


})();

function addSectionToDeleteToList(sectionToDeleteId)
{

}

function saveChanges()
{
    let layout = [];
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
        console.log(panels);

        let panelsCount = panels.length;

        let panelStructure = [];

        for (let j = 0; j < panelsCount; j++)
        {
            console.log(panels[j].id);

            let panel = {
                panelId: panels[j].id,
                panelSize: panels[j]['attributes']['size'].value,
                panelState: panels[j]['attributes']['state'].value
            };
            panelStructure.push(panel);
        }

        let sectionObject = {
            sectionName : sectionName,
            sectionId : sectionId,
            panelStructure: panelStructure
        }

        layout.push(sectionObject);




            }
    console.log(layout);
}

