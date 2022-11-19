package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class CrudController {


    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public CrudController(UserService userService,
                          RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getUserLsit(Model model) {
        List<User>  userList = userService.allUsers();
        model.addAttribute("userList",userList);
        return "/list";
    }


    @GetMapping("/{id}")
    public String getByID(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserBYId(id));
        return "userPage";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin";
    }


    @GetMapping("/edit/{id}")
        public String edit(@PathVariable("id") Long id, Model model ) {
        model.addAttribute("user", userService.findUserBYId(id));
        return "/edit";
    }


    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute User user, @PathVariable("id") Long id) {
        userService.updateUser(id,user);
        return "redirect:/users";
    }


    @GetMapping("/user")
    public String index(Principal principal, Model model){
        User user =  userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "/user";
    }

    @GetMapping("/index")
    public String index2() {
        return "/index";
    }
}
