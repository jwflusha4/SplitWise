package com.shaw.SplitWise1.Dto;
import com.shaw.SplitWise1.Model.ExpenseType;
import com.shaw.SplitWise1.Model.ExpenseUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
@Getter
@Setter
@ToString
public class ExecuteExpenseRequest {
    private long groupId;
    private ExpenseType type;

}
