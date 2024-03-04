package com.shaw.SplitWise1.Dto;

import com.shaw.SplitWise1.Model.ExpenseType;
import lombok.Data;

import java.util.List;

@Data
public class CreatingExpenseRequest {
    private long groupId;
    private String description;
    private int amount;
    private List<Pair> values;
    private ExpenseType type;
    private List<String> ownedBy;
}
