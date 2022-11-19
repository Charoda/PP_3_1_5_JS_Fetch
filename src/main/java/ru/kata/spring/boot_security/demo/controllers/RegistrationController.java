package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
public class RegistrationController {

    private final RoleServiceImpl roleService;
    private final UserService userService;
    @Autowired
    public RegistrationController(RoleServiceImpl roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/registration")
        public String register(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.listRoles());
            return "/registration";
    }

    @PostMapping("/registration")
        public String performRegister(@ModelAttribute("user") User user,
                                      @RequestParam("roles") Long[] rolesId) {
            for  (Long id : rolesId ) {
                user.addRole(roleService.getRole(id));
            }
            if (userService.saveUser(user)) {
                return "/registSuccess";
            }
            return "/index";
    }

}
