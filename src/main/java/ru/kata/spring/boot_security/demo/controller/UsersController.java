package ru.kata.spring.boot_security.demo.controller;

import javafx.scene.canvas.GraphicsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping( "/")
    public String registration(@ModelAttribute("user") User user) {
        System.out.println("Перешел на reg");
        return "registration";
    }
    @PostMapping("/registration")
    public String register(@ModelAttribute("user") User user) {
        System.out.println("ADD   " + user.getUsername());
        userService.save(user);
        return "login";
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        System.out.println("Go to user " + principal.getName());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user";
    }
}
