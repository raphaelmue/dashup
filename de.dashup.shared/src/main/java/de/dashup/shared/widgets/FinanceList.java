package de.dashup.shared.widgets;

import java.util.List;

public class FinanceList {

    private List<SpendingList> financeList;

    public FinanceList(){
        super();
    }

    public FinanceList(List<SpendingList> financeList){
        this.financeList = financeList;
    }

    public List<SpendingList> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(List<SpendingList> financeList) {
        this.financeList = financeList;
    }

}