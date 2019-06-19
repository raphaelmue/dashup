package de.dashup.shared.widgets;

import java.util.List;

public class FinanceList {

    private List<SpendingList> spendingList;

    public FinanceList(){
        super();
    }

    public FinanceList(List<SpendingList> spendingList) {
        this.spendingList = spendingList;
    }

    public List<SpendingList> getFinanceList() {
        return spendingList;
    }

    public void setFinanceList(List<SpendingList> financeList) {
        this.spendingList = financeList;
    }

}