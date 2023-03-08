package com.example.chatapplication.service;

import com.example.chatapplication.dao.UserRepository;
import com.example.chatapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public int saveUser(User user) {
        User userObj = userRepository.save(user);
        return userObj.getUserId();
    }
}
