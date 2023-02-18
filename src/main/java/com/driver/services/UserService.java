package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository3;

    public User createUser(String username, String password){
        User user = new User(username,password);
        //cannot do anything of list of blogs right now

        //now save user
        userRepository3.save(user);
        return user;
    }

    public void deleteUser(int userId) throws Exception {
        if(userId > 0) {
            userRepository3.deleteById(userId);
        }else {
            throw new Exception("Id is not valid");
        }
    }

    public User updateUser(Integer id, String password) throws NoSuchElementException {
        User user = userRepository3.findById(id).get();
        //set basic attributes
        user.setUsername(user.getUsername());
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setBlogList(user.getBlogList());

        //now set password
        user.setPassword(password);
        return user;
    }
}
