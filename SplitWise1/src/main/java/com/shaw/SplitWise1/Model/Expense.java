package com.shaw.SplitWise1.Model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Entity
public class Expense extends BaseModel{
    private String description;
    private int totalAmount;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;
    @OneToMany
    List<ExpenseUser> paidBy;
    @OneToMany
    List<ExpenseUser> owedBY;
}
