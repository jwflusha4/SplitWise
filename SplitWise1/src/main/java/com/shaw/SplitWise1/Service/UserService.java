package com.shaw.SplitWise1.Service;

import com.shaw.SplitWise1.Model.User;
import com.shaw.SplitWise1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }
}
