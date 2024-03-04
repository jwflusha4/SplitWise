package com.shaw.SplitWise1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Entity
public class ExpenseUser extends BaseModel{
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private GivingType status;
}
