package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UsersRestController {

    private final UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.allUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserBYId(id);
    }

    @PostMapping("")
    public User addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user.getId(),user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
