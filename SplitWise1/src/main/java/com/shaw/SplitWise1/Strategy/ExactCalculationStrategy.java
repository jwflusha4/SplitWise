package com.shaw.SplitWise1.Strategy;



import com.shaw.SplitWise1.Model.ExpenseUser;
import com.shaw.SplitWise1.Model.GivingType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExactCalculationStrategy implements CalculationStrategy{
    @Override
    public List<ExpenseUser> calculate(List<ExpenseUser> paidBy, List<ExpenseUser> ownedBy) {
        //ExpenseUserDto expenseUserDto=new ExpenseUserDto();
        Scanner sc=new Scanner(System.in);
        //int totalPaidBy= paidBy.size();
        //int totalOwnedBy=ownedBy.size();
        //int avgAmount=totalAmount/(totalPaidBy+totalOwnedBy);
        for(int i=0;i<ownedBy.size();i++){
            System.out.println("Enter amount - ");
            int val=sc.nextInt();
            ownedBy.get(i).setAmount(val);
        }
        List<ExpenseUser> listAfterSplit=new ArrayList<>();
        int i=0,j=0;
        while(j<ownedBy.size() && i<paidBy.size()){
            //System.out.println("inside3");
            int val1=paidBy.get(i).getAmount();
            int val2=ownedBy.get(j).getAmount();
            if(val1==val2){
                ExpenseUser temp=new ExpenseUser();
                temp.setFromUser(ownedBy.get(j).getFromUser());
                temp.setToUser(paidBy.get(i).getFromUser());
                temp.setAmount(val1);
                temp.setStatus(GivingType.GIVING);
                listAfterSplit.add(temp);
//                paidBy.get(j).setFromUser(ownedBy.get(j).getFromUser());
//                paidBy.get(j).setToUser(paidBy.get(i).getFromUser());
//                paidBy.get(j).setAmount(val1);
//                paidBy.get(j).setStatus(GivingType.GIVING);
                i++;
                j++;
                //System.out.println(temp.toString());
            }
            else if(val1>val2){
                ExpenseUser temp=new ExpenseUser();
                temp.setFromUser(ownedBy.get(j).getFromUser());
                temp.setToUser(paidBy.get(i).getFromUser());
                temp.setAmount(val2);
                temp.setStatus(GivingType.GIVING);
                listAfterSplit.add(temp);
//                paidBy.get(j).setFromUser(ownedBy.get(j).getFromUser());
//                paidBy.get(j).setToUser(paidBy.get(i).getFromUser());
//                paidBy.get(j).setAmount(val2);
//                paidBy.get(j).setStatus(GivingType.GIVING);
                paidBy.get(i).setAmount(paidBy.get(i).getAmount()-val2);
                j++;
                //System.out.println(temp.toString());
            }
            else{
                ExpenseUser temp=new ExpenseUser();
                temp.setFromUser(ownedBy.get(j).getFromUser());
                temp.setToUser(paidBy.get(i).getFromUser());
                temp.setAmount(val1);
                temp.setStatus(GivingType.GIVING);
                listAfterSplit.add(temp);
//                paidBy.get(j).setFromUser(ownedBy.get(j).getFromUser());
//                paidBy.get(j).setToUser(paidBy.get(i).getFromUser());
//                paidBy.get(j).setAmount(val1);
//                paidBy.get(j).setStatus(GivingType.GIVING);
                ownedBy.get(j).setAmount(ownedBy.get(i).getAmount()-val1);
                i++;
            }
        }
        return listAfterSplit;
    }
}
