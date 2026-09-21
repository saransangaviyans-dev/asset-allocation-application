package com.dev.application.controller;


import com.dev.application.entity.User;
import com.dev.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

@CrossOrigin(origins = "*")
public class UserController{
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){

        System.out.println("User Routing");
        return userService.getAllUsers();
    }

    @PostMapping
    public User createusera(@RequestBody User user){
        return userService.saveUser(user);
    }
}
