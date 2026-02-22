package com.example.finance_tracker;

import java.util.List;

public class DebtCalculator {

    public static double calculateTotalBalance(List<debt> debts) {

        double totalOwedToMe = 0.0;
        double totalIOwe = 0.0;

        for (debt debt : debts) {

            // Skip fully settled debts
            if (debt.isSettled()) continue;

            // Remaining amount (handles partial payments)..
            double remainingAmount = debt.getRemainingAmount();

            if (debt.getDebtType() == DebtType.OWED_TO_ME) {
                totalOwedToMe += remainingAmount;
            }
            else if (debt.getDebtType() == DebtType.I_OWE) {
                totalIOwe += remainingAmount;
            }
        }

        return totalOwedToMe - totalIOwe;
    }
}