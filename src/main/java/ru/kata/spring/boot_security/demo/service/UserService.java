package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
     void save(User user);
     void delete(Long id);
     List<User> listUsers();
     User showUser(long id);
     void update(User user);
     User findByUsername(String name);
}