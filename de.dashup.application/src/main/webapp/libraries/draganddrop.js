let sectionsToDelete = [];
let panelsToDelete = [];
let selectedPanel;

(function () {
    initializeDragAndDrop();
    initializePanelDropdown();
    initializeSectionDeleteClick();
    initializePanelDeleteClick();
})();

function initializeDragAndDrop() {
    dragula([document.querySelector('.drag-drop-container')], {
        moves: function (el, container, handle) {

            let draggedClass = handle.classList[0];

            if (draggedClass === 'drag-drop-btn') {
                return !handle.classList.contains('bloc--inner');
            }
        }
    });

    dragula([].slice.apply(document.querySelectorAll('.bloc')), {
        direction: 'horizontal'
    });
}

function initializePanelDropdown() {
    document.addEventListener('DOMContentLoaded', function () {
        let elems = document.querySelectorAll('.dropdown-trigger');
        M.Dropdown.init(elems, null);
    });

    $(".dropdown-trigger").on("click", function (event) {
        let panel = event.currentTarget.parentNode.parentNode.parentNode;
        selectedPanel = panel.id;
    });

}

function initializeSectionDeleteClick() {
    $(".section-minus").on("click", function (event) {
        let sectionToDelete = event.currentTarget.parentNode.parentNode.parentNode;

        addSectionToDeleteToList(sectionToDelete);

        while (sectionToDelete.firstChild) {
            sectionToDelete.removeChild(sectionToDelete.firstChild);
        }

        let sectionParent = sectionToDelete.parentNode;
        sectionParent.removeChild(sectionToDelete);

    });
}

function initializePanelDeleteClick() {
    $("#delete").on("click", function () {
        console.log("delete pressed");
        let panelToDelete = document.getElementById(selectedPanel);
        let panelToDeleteParent = panelToDelete.parentNode;

        panelsToDelete.push(panelToDelete.id);

        panelToDeleteParent.removeChild(panelToDelete);

    });
}

function addSectionToDeleteToList(sectionToDelete) {
    let section = sectionToDelete.childNodes[1];
    let panels = section.childNodes;

    let panelStructure;
    panelStructure = makePanelStructure(panels);

    let sectionObject = {
        sectionName: "",
        sectionId: sectionToDelete.id,
        panelStructure: panelStructure,
        remove: true
    };

    sectionsToDelete.push(sectionObject);
}

function makeSectionPanelOrder() {
    let sectionPanelOrder = [];
    let dragDropContainer = document.getElementById('drag-drop-container');
    let sections = dragDropContainer.getElementsByClassName('wrapper');
    let sectionsCount = sections.length;

    for (let i = 0; i < sectionsCount; i++) {

        let sectionName = sections[i]['children'][0]['innerText'];
        let sectionId = sections[i].id;
        let panels = sections[i]['children'][1]['children'];
        let panelStructure = makePanelStructure(panels);

        let sectionObject = {
            sectionName: sectionName,
            sectionId: sectionId,
            panelStructure: panelStructure,
        };

        sectionPanelOrder.push(sectionObject);
    }

    return sectionPanelOrder
}

function saveChanges() {
    let sectionPanelOrder = makeSectionPanelOrder();
    let layout = {
        panelsToDelete: panelsToDelete,
        sectionsToDelete: sectionsToDelete,
        sectionPanelOrder: sectionPanelOrder
    };

    console.log(layout);
}

function makePanelStructure(panels) {
    let panelsCount = panels.length;
    let panelStructure = [];

    for (let j = 0; j < panelsCount; j++) {
        let panel = {
            panelId: panels[j].id,
            panelSize: panels[j]['attributes']['size'].value,
        };
        panelStructure.push(panel);
    }

    return panelStructure;
}

