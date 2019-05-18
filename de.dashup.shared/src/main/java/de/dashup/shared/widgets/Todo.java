package de.dashup.shared.widgets;

import java.util.List;

public class Todo {

    private List<Entry> list;

    public Todo(){
        super();
    }

    public Todo(List<Entry> list){
        this.list = list;
    }

    public List<Entry> getList() {
        return list;
    }

    public void setList(List<Entry> list) {
        this.list = list;
    }

}