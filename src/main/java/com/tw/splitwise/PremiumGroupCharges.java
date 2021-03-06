package com.tw.splitwise;

import java.util.List;

import static com.tw.splitwise.Constant.*;

//This class represents premium user charges to be paid
public class PremiumGroupCharges implements Charges {
    private final double splitwiseRate;

    public PremiumGroupCharges(double splitwiseRate) {
        this.splitwiseRate = splitwiseRate;
    }

    @Override
    public void calculate(List<Friend> friends, List<Bill> bills) {
        Double totalAmount = Bill.findTotalAmount(bills);
        Integer noOfFriends = friends.size();
        Double charge = (splitwiseRate * totalAmount) / noOfFriends;

        SettlementAmount settlementAmount = null;

        Friend splitwiseAdmin = new Friend(splitwiseAdminName, defaultAmountPaid, defaultAmountToPay);
        for (Friend friend : friends) {
            settlementAmount = new SettlementAmount(charge, splitwiseAdmin);
            friend.addSettlementAmount(settlementAmount);
        }
    }
}