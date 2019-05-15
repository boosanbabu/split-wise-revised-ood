package com.tw.splitwise;

import java.util.List;
import java.util.Map;

//This class represents statement of the money owed for services
class Bill {
    private final Double amount;
    private final BillType type;
    private final Friend paidBy;
    private final List<Friend> paidFor;
    private final Map<Friend, Double> expenseRatio;

    private final static Double defaultExpenseRatio = 1.0;

    Bill(Double amount, BillType type, Friend paidBy, List<Friend> paidFor, Map<Friend, Double> expenseRatio) {
        this.amount = amount;
        this.type = type;
        this.paidBy = paidBy;
        this.paidFor = paidFor;
        this.expenseRatio = expenseRatio;
    }

    void settle() {
        int friendsCount = paidFor.size();
        Double perHeadAmount = (this.amount / friendsCount);
        for (Friend friend : this.paidFor) {
            Double ratio = findExpenseRatio(friend);
            Double ratioSpecificAmount = ratio * perHeadAmount;
            friend.updateAmountToPay(ratioSpecificAmount);
        }
    }

    private Double findExpenseRatio(Friend friend) {
        if (this.expenseRatio == null)
            return defaultExpenseRatio;
        return expenseRatio.getOrDefault(friend, defaultExpenseRatio);
    }
}