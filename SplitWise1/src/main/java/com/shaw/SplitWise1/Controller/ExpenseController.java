package com.shaw.SplitWise1.Controller;

import com.shaw.SplitWise1.Dto.*;
import com.shaw.SplitWise1.Model.Expense;
import com.shaw.SplitWise1.Model.ExpenseUser;
import com.shaw.SplitWise1.Model.User;
import com.shaw.SplitWise1.Repository.UserRepository;
import com.shaw.SplitWise1.Service.ExpenseService;
import com.shaw.SplitWise1.Service.SplitService;
import com.shaw.SplitWise1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sw")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private SplitService splitService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PostMapping("/addExpense")
    public CreatingExpenseResponse createExpense(@RequestBody CreatingExpenseRequest request) {
        List<User> userList = new ArrayList<>();
        List<User> ownedUserList = new ArrayList<>();
        List<Integer> valueList = new ArrayList<>();

        // Save or retrieve users
        for (Pair pair : request.getValues()) {
            User user = userRepository.findByName(pair.getUser());
            if (user == null) {
                // User does not exist, create a new one
                user = new User();
                user.setName(pair.getUser());
                userRepository.save(user); // Save the user
            }
            userList.add(user);
            valueList.add(pair.getAmount());
        }
        for(String it:request.getOwnedBy()){
            User user = userRepository.findByName(it);
            if (user == null) {
                // User does not exist, create a new one
                user=userService.createUser(it);
                userRepository.save(user); // Save the user
            }
            ownedUserList.add(user);
            //valueList.add(0);
        }
        // Call service method to add expense
        expenseService.addExpense(request.getGroupId(), request.getDescription(),
                request.getAmount(), userList, valueList, request.getType(),
                ownedUserList);

        return new CreatingExpenseResponse("SUCCESS");
    }
    @PostMapping("/executeExpense")
    public ExecuteExpenseResponse executeExpense(@RequestBody ExecuteExpenseRequest request){
        List<ExpenseUser> result=splitService.executeExpense(request.getGroupId(),request.getType());
        for(ExpenseUser it:result){
            System.out.println(it.getFromUser().getName()+"----"+it.getToUser().getName()+" = "+it.getAmount());
        }
        System.out.println(result.size());
        return new ExecuteExpenseResponse("SUCCESS");
    }
}
