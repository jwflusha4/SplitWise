package com.shaw.SplitWise1.Repository;

import com.shaw.SplitWise1.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Long > {
}
