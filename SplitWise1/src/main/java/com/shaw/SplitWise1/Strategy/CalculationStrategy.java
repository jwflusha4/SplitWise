package com.shaw.SplitWise1.Strategy;


import com.shaw.SplitWise1.Model.ExpenseUser;

import java.util.List;

public interface CalculationStrategy {
    public List<ExpenseUser> calculate(List<ExpenseUser>paidBy, List<ExpenseUser>ownedBy);
}
