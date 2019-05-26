package de.dashup.shared.widgets;

import java.util.List;

public class Todo {

    private List<Task> list;

    public Todo(){
        super();
    }

    public Todo(List<Task> list){
        this.list = list;
    }

    public List<Task> getList() {
        return list;
    }

    public void setList(List<Task> list) {
        this.list = list;
    }

}