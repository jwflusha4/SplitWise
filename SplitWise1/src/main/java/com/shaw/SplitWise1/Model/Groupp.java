package com.shaw.SplitWise1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Groupp extends BaseModel{
    private String name;
//    @ManyToOne
//    private User ownerUser;
    @OneToMany
    private List<User> users;
    @OneToMany
    private List<Expense> expenses;
}
