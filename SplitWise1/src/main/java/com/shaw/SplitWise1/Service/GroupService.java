package com.shaw.SplitWise1.Service;

import com.shaw.SplitWise1.Model.Groupp;
import com.shaw.SplitWise1.Model.User;
import com.shaw.SplitWise1.Repository.GroupRepository;
import com.shaw.SplitWise1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    public Groupp createGroup(List<User> userList, String name){
        Groupp groupp =new Groupp();
        //group.setDescription(description);
        //groupp.setOwnerUser(owner);
        for(User user:userList){
            userRepository.save(user);
        }
        groupp.setUsers(userList);
        groupp.setExpenses(new ArrayList<>());
        groupp.setName(name);
        return groupRepository.save(groupp);
    }
}
