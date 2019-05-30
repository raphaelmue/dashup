package de.dashup.shared.widgets;

public class SpendingChart {

    private String category;
    private int value;

    public SpendingChart() {
        super();
    }

    public SpendingChart(String category, int value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(int value) {
        this.value = value;
    }

}