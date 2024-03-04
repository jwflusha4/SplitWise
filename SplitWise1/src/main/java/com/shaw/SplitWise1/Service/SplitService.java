package com.shaw.SplitWise1.Service;

import com.shaw.SplitWise1.Model.ExpenseType;
import com.shaw.SplitWise1.Model.ExpenseUser;
import com.shaw.SplitWise1.Model.GivingType;
import com.shaw.SplitWise1.Model.User;
import com.shaw.SplitWise1.Repository.ExpenseUserRepository;
import com.shaw.SplitWise1.Strategy.CalculationStrategy;
import com.shaw.SplitWise1.Strategy.EqualCalculationStrategy;
import com.shaw.SplitWise1.Strategy.ExactCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SplitService {
    @Autowired
    private ExpenseUserRepository expenseUserRepository;
    public List<ExpenseUser> executeExpense(long groupId, ExpenseType type){
        CalculationStrategy calculationStrategy=generateStrategy(type);
        List<ExpenseUser> paidBy=expenseUserRepository.findByGroupIdAndGetting(groupId);
        List<ExpenseUser> ownedBy=expenseUserRepository.findByGroupIdAndGiving(groupId,GivingType.GIVING);
        System.out.println("paidBy = "+paidBy.size()+" ownedBy = "+ownedBy.size());
        for(ExpenseUser it:paidBy){
            System.out.println(it.toString());
        }
        for(ExpenseUser it:ownedBy){
            System.out.println(it.toString());
        }
        System.out.println("SplitService "+ownedBy.size());
        List<ExpenseUser> finalPaidBy=sumAmountByFromUser(paidBy,GivingType.GETTING);
        List<ExpenseUser> finalOwnedBy=sumAmountByFromUser(ownedBy,GivingType.GIVING);
        for(ExpenseUser it:finalPaidBy){
            for(ExpenseUser et:finalOwnedBy){
                if(it.getFromUser()==et.getFromUser() && et.getAmount()==0){
                    finalOwnedBy.remove(et);
                    break;
                }
            }
        }
        List<ExpenseUser> expenseUserList=calculationStrategy.calculate(finalPaidBy,finalOwnedBy);

        return (expenseUserList);
    }
    private CalculationStrategy generateStrategy(ExpenseType type){
        if(type==ExpenseType.EQUAL){
            return new EqualCalculationStrategy();
        }
        if(type==ExpenseType.EXACT){
            return new ExactCalculationStrategy();
        }
        return null;
    }
    public List<ExpenseUser> sumAmountByFromUser(List<ExpenseUser> expenseUsers,GivingType type) {
        // Group ExpenseUser objects by fromUser
        Map<User, Integer> amountByFromUser = expenseUsers.stream()
                .collect(Collectors.groupingBy(ExpenseUser::getFromUser,
                        Collectors.summingInt(ExpenseUser::getAmount)));

        // Create a list to store the result
        List<ExpenseUser> result = new ArrayList<>();

        // Iterate through the map entries and create ExpenseUser objects with summed amounts
        for (Map.Entry<User, Integer> entry : amountByFromUser.entrySet()) {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setFromUser(entry.getKey());
            expenseUser.setAmount(entry.getValue());
            expenseUser.setStatus(type);
            result.add(expenseUser);
        }

        return result;
    }
}

