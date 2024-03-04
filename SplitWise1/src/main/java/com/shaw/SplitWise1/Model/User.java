package com.shaw.SplitWise1.Model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
@Data
@Entity
public class User extends BaseModel{
    private String name;
    private String phone;
    private String email;


}
