package sber.ru.terminal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.ru.terminal.entity.User;
import sber.ru.terminal.services.UserService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public User addUser(@RequestBody User user){
        userService.registration(user);
        return user;
    }

    @GetMapping("/user/{user_id}")
    public User getUserById(@PathVariable("user_id") Long userId) {
        return userService.getUserById(userId);
    }
}