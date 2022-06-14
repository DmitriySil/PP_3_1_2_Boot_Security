package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminsController {

    private final UserService userService;

    @Autowired
    public AdminsController(UserService userServiceImp) {
        this.userService = userServiceImp;
    }


    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String showUser(Principal principal, Model model) {
        System.out.println("Go to user " + principal.getName());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user";
    }
    @GetMapping("/admin/user/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "user";
    }

    @GetMapping( "/admin/user/add")
    public String addUser(@ModelAttribute("user") User user) {
        return "add";
    }
    @PostMapping("/admin/user/add")
    public String add(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/user/{id}/edit")
    public String edit(Model model,@PathVariable("id") Long id) {
        model.addAttribute("user", userService.showUser(id));
        return "edit";
    }
    @GetMapping("/admin/user/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
    @PostMapping("/admin/user/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(user);
        return "redirect:/admin/users";
    }
}