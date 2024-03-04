package com.shaw.SplitWise1.Controller;

import com.shaw.SplitWise1.Dto.CreatingGroupRequest;
import com.shaw.SplitWise1.Dto.CreatingGroupResponse;
import com.shaw.SplitWise1.Model.User;
import com.shaw.SplitWise1.Service.GroupService;
import com.shaw.SplitWise1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sw")
public class GroupController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @PostMapping("/createGroup")
    public CreatingGroupResponse createGroup(@RequestBody CreatingGroupRequest request){
//        User leader = userService.createUser(request.getOwnerOfGroup());
//        leader.setName(request.getOwnerOfGroup());
        List<User> userList = new ArrayList<>();
        for(String userName : request.getOtherUser()){
            User user = userService.createUser(userName);
            userList.add(user);
        }
        groupService.createGroup(userList,request.getName());
        return new CreatingGroupResponse("SUCCESS");
    }
}
