package de.dashup.shared.widgets;

import java.util.List;

public class FinanceChart {

    private List<SpendingChart> chart;

    public FinanceChart(){
        super();
    }

    public FinanceChart(List<SpendingChart> chart){
        this.chart = chart;
    }

    public List<SpendingChart> getChart() {
        return chart;
    }

    public void setChart(List<SpendingChart> chart) {
        this.chart = chart;
    }

}