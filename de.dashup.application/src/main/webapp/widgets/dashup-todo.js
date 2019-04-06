import {LitElement, html, css} from 'https://unpkg.com/lit-element@2.1.0/lit-element.js?module'
export class DashupTodo extends LitElement{

    render() {
        return html`
            <dashup-grid-layout>
                <dashup-text-input name="todo"
                                   placeholder='Enter a task here...' 
                                   layout='{"row": 1, "offset": 2, "size": 4}'> 
                </dashup-text-input>
                <dashup-button text="Add"
                               consumers="list" 
                               producers="todo"
                               layout='{"row": 1, "offset": 1, "size": 1}'>
                </dashup-button>
                <dashup-list name="list"
                             path="todo"
                             selectable="true"
                             layout='{"row": 2, "offset": 2, "size": 4}'>
                </dashup-list>
                <dashup-grid-layout layout='{"row": 2, "offset": 0, "size": 6}'>
                    <dashup-button text="Delete"
                                   consumers="list" 
                                   producers="list"
                                   mode="delete"
                                   layout='{"row": 1, "offset": 0, "size": 1}'>
                    </dashup-button>
                    <dashup-button text="Save"
                                   producers="list" 
                                   layout='{"row": 2, "offset": 0, "size": 1}'
                                   disabled>
                    </dashup-button>
                </dashup-grid-layout>
            </dashup-grid-layout>
        `;
    }

}
customElements.define("dashup-todo",DashupTodo);