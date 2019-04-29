let sectionsToDelete = [];
let selectedPanel;
let globalSectionCount;
let section_container;
let panel_container;
(function () {
    initializeDragAndDrop();
    initializePanelDropdown();
    initializeSectionDeleteClick();
    initializePanelDeleteClick();
    initializeAddSectionButtonClick();
    initializeSaveChangesButtonClick();

    globalSectionCount = 0;

})();

function initializeDragAndDrop() {
    section_container = dragula([document.querySelector('.drag-drop-container')], {
        moves: function (el, container, handle) {

            let draggedClass = handle.classList[0];

            if (draggedClass === 'drag-drop-btn') {
                return !handle.classList.contains('bloc--inner');
            }
        }
    });

    panel_container = dragula([].slice.apply(document.querySelectorAll('.bloc')), {
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

        if (sectionToDelete != null) {
            addSectionToDeleteToList(sectionToDelete);

            while (sectionToDelete.firstChild) {
                sectionToDelete.removeChild(sectionToDelete.firstChild);
            }

            let sectionParent = sectionToDelete.parentNode;
            sectionParent.removeChild(sectionToDelete);
        }

    });
}

function initializeAddSectionButtonClick() {
    $("#add-section-button").on("click", function () {
        let sectionId = "n" + globalSectionCount;
        let panelContainerToAdd = addNewSection(sectionId);
        panel_container.containers.push(panelContainerToAdd);
        initializeSectionDeleteClick();
    });
}

function initializeSaveChangesButtonClick() {
    $("#save-changes-button").on("click", function () {
        saveChanges();
    });
}

function initializePanelDeleteClick() {
    $("#delete").on("click", function () {
        let panelToDelete = document.getElementById(selectedPanel);
        let panelToDeleteParent = panelToDelete.parentNode;

        panelToDeleteParent.removeChild(panelToDelete);

    });
}

function addNewSection(sectionId) {

    let dragAndDropContainer = document.getElementById('drag-drop-container');

    let wrapper = document.createElement("div");
    wrapper.setAttribute("class", "wrapper  col s12");
    wrapper.setAttribute("id", sectionId);

    let row = document.createElement("div");
    row.setAttribute("class", "row");

    let sectionHeading = document.createElement("div");
    sectionHeading.setAttribute("class", "drag-drop-btn col s6 valign-wrapper");

    let gripLineIcon = document.createElement("i");
    gripLineIcon.setAttribute("class", "drag-drop-btn fas fa-grip-lines col s1");
    gripLineIcon.setAttribute("style", "margin:0");

    let inputField = document.createElement("input");
    inputField.setAttribute("class", "col s4");
    inputField.setAttribute("type", "text");
    inputField.setAttribute("style", "margin:0");
    inputField.setAttribute("value", "New Section");

    let minusIcon = document.createElement("i");
    minusIcon.setAttribute("class", "section-minus fas fa-minus col s1");
    minusIcon.setAttribute("style", "margin:0");

    let panelContainer = document.createElement("div");
    panelContainer.setAttribute("class", "bloc col s12");

    sectionHeading.appendChild(gripLineIcon);
    sectionHeading.appendChild(inputField);
    sectionHeading.appendChild(minusIcon);

    row.appendChild(sectionHeading);

    wrapper.appendChild(row);
    wrapper.appendChild(panelContainer);

    dragAndDropContainer.appendChild(wrapper);

    return panelContainer;
}

function addSectionToDeleteToList(sectionToDelete) {
    let section = sectionToDelete.childNodes[1];
    let panels = section.childNodes;

    let panelStructure;
    panelStructure = makePanelStructure(panels);

    if (sectionsToDelete.id !== "n0") {
        let sectionObject = {
            sectionName: "",
            sectionId: sectionToDelete.id,
            panelStructure: panelStructure,
        };

        sectionsToDelete.push(sectionObject);
    }

}

function makeSectionPanelOrder() {
    let sectionPanelOrder = [];
    let dragDropContainer = document.getElementById('drag-drop-container');
    let sections = dragDropContainer.getElementsByClassName('wrapper');
    let sectionsCount = sections.length;

    for (let i = 0; i < sectionsCount; i++) {

        let sectionName = sections[i].children[0].children[0].children[1].value;
        let sectionId = sections[i].id;
        let panels = sections[i].children[1].children;
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

function saveChanges() {
    let sectionPanelOrder = makeSectionPanelOrder();
    let layout = {
        sectionsToDelete: sectionsToDelete,
        sectionPanelOrder: sectionPanelOrder
    };

    postChanges(layout);
}

function postChanges(data) {
    let url = '/layoutMode/handleSaveChanges';

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error("error");
        }
        return response.json();

    }).then(() => {
        showSaveReponseSuccessMessageToast()
    }).catch(() => {
        showSaveResponseErrorMessageToast()
    });

}