package com.shaw.SplitWise1.Strategy;


import com.shaw.SplitWise1.Model.ExpenseUser;
import com.shaw.SplitWise1.Model.GivingType;
import com.shaw.SplitWise1.Model.User;

import java.util.ArrayList;
import java.util.List;

public class EqualCalculationStrategy implements CalculationStrategy {

    @Override
    public List<ExpenseUser> calculate(List<ExpenseUser> paidBy, List<ExpenseUser> ownedBy) {
        int totalPaidBy= paidBy.size();
        int totalOwnedBy=ownedBy.size();
        int totalAmount=0;
        //System.out.println("inside1");
        for(int i=0;i<paidBy.size();i++){
            totalAmount+=paidBy.get(i).getAmount();
        }
        for(int i=0;i<ownedBy.size();i++){
            totalAmount+=ownedBy.get(i).getAmount();
        }
        int avgAmount=totalAmount/(totalPaidBy+totalOwnedBy);
        for(int i=0;i<paidBy.size();i++){
            if(paidBy.get(i).getAmount()<avgAmount){
                paidBy.get(i).setStatus(GivingType.GIVING);
                ownedBy.add(paidBy.get(i));
                paidBy.remove(paidBy.get(i));
            }
        }
        for(ExpenseUser it:paidBy){
            System.out.println(it.toString());
        }
        for(ExpenseUser it:ownedBy){
            System.out.println(it.toString());
        }
        System.out.println("total_amount = "+totalAmount+" avg_amount = "+avgAmount);
        List<ExpenseUser>listAfterSplit=new ArrayList<>();
        int i = 0, j = 0;
        while (j < ownedBy.size() && i < paidBy.size()) {
            int val1 = paidBy.get(i).getAmount() - avgAmount;
            int val2 = avgAmount - ownedBy.get(j).getAmount();

            if (val1 == val2) {
                listAfterSplit.add(createExpenseUser(ownedBy.get(j).getFromUser(), paidBy.get(i).getFromUser(), val1));
                i++;
                j++;
            } else if (val1 > val2) {
                listAfterSplit.add(createExpenseUser(ownedBy.get(j).getFromUser(), paidBy.get(i).getFromUser(), val2));
                paidBy.get(i).setAmount(paidBy.get(i).getAmount() - val2);
                j++;
            } else {
                listAfterSplit.add(createExpenseUser(ownedBy.get(j).getFromUser(), paidBy.get(i).getFromUser(), val1));
                ownedBy.get(j).setAmount(val1);
                i++;
            }
        }
        return listAfterSplit;
    }

    private int getTotalAmount(List<ExpenseUser> expenseUsers) {
        int totalAmount = 0;
        for (ExpenseUser expenseUser : expenseUsers) {
            totalAmount += expenseUser.getAmount();
        }
        return totalAmount;
    }

    private ExpenseUser createExpenseUser(User fromUser, User toUser, int amount) {
        ExpenseUser expenseUser = new ExpenseUser();
        expenseUser.setFromUser(fromUser);
        expenseUser.setToUser(toUser);
        expenseUser.setAmount(amount);
        expenseUser.setStatus(GivingType.GIVING);
        return expenseUser;
    }
}