package com.shaw.SplitWise1.Dto;

import lombok.Data;

import java.util.List;
@Data
public class CreatingGroupRequest {
    private String name;
    private String ownerOfGroup;
    private List<String> otherUser;
}
