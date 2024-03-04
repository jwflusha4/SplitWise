package com.shaw.SplitWise1.Service;

import com.shaw.SplitWise1.Model.*;
import com.shaw.SplitWise1.Repository.ExpenseRepository;
import com.shaw.SplitWise1.Repository.ExpenseUserRepository;
import com.shaw.SplitWise1.Repository.GroupRepository;
import com.shaw.SplitWise1.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ExpenseService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseUserRepository expenseUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public Groupp addExpense(long groupId, String description, int amount,
                             List<User> userList, List<Integer> valueList,
                             ExpenseType type, List<User> ownedBy) {

        // Retrieve the group entity
        Groupp group = groupRepository.getById(groupId);

        // Save or retrieve users
        List<User> savedUsers = new ArrayList<>();
        List<User> ownedSavedUsers = new ArrayList<>();
        for (User user : userList) {
            User savedUser = userRepository.findByName(user.getName());
            if (savedUser == null) {
                savedUser = userRepository.save(user);
            }
            savedUsers.add(savedUser);
        }
        for (User user : ownedBy) {
            User savedUser = userRepository.findByName(user.getName());
            if (savedUser == null) {
                savedUser = userRepository.save(user);
            }
            ownedSavedUsers.add(savedUser);
        }

        // Generate and save ExpenseUser entities
        List<ExpenseUser> expensePaidUserList = new ArrayList<>();
        for (int i = 0; i < savedUsers.size(); i++) {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setFromUser(savedUsers.get(i));
            expenseUser.setAmount(valueList.get(i));
            expenseUser.setStatus(GivingType.GETTING);
            expensePaidUserList.add(expenseUserRepository.save(expenseUser));
        }

        // Generate and save owned ExpenseUser entities
//        List<ExpenseUser> expenseOwnedUserList = generateOwnedExpenseUser(ownedBy);
        List<ExpenseUser> expenseOwnedUserList = new ArrayList<>();
        for (int i = 0; i < ownedSavedUsers.size(); i++) {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setFromUser(ownedSavedUsers.get(i));
            expenseUser.setAmount(0);
            expenseUser.setStatus(GivingType.GIVING);
            expenseOwnedUserList.add(expenseUserRepository.save(expenseUser));
        }
        // Create Expense entity
        Expense expense = new Expense();
        expense.setExpenseType(type);
        expense.setDescription(description);
        expense.setPaidBy(expensePaidUserList);
        expense.setOwedBY(expenseOwnedUserList);
        expense.setTotalAmount(amount);
        System.out.println("expensePaidUserList.size() = "+expensePaidUserList.size()+" expenseOwnedUserList.size() = "+expenseOwnedUserList.size());
        // Save Expense entity
        Expense savedExpense = expenseRepository.save(expense);

        // Add Expense to group and save
        group.getExpenses().add(expense);
        return groupRepository.save(group);
    }

    private List<ExpenseUser> generateOwnedExpenseUser(List<String> userList){
        List<ExpenseUser> expenseUserList=new ArrayList<>();
        int count=0;
        for(String it:userList){

//            User user=new User();
//            user.setName(it);
//            // Save user before creating ExpenseUser
//            User savedUser = userRepository.save(user);
            User user=userRepository.findByName(it);
            if(user!=null){
                count++;
                ExpenseUser expenseUser=new ExpenseUser();
                expenseUser.setFromUser(user);
                expenseUser.setAmount(0);
                expenseUser.setStatus(GivingType.GIVING);
                expenseUserList.add(expenseUserRepository.save(expenseUser));
            }
        }
        System.out.println("count = "+count);
        return expenseUserList;
    }
}
