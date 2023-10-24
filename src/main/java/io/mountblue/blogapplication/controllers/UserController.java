package io.mountblue.blogapplication.controllers;

import io.mountblue.blogapplication.entities.Users;
import io.mountblue.blogapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String loginHandler() {
        return "User/Login";
    }

    @GetMapping("/access-denied")
    public String accessDeniedHandler() {
        return "User/AccessDenied";
    }


    @GetMapping("/register")
    public String registerUser(Model model) {
        return "User/Register";
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public String processRegisterUser(@ModelAttribute Users newUsers, Model model) {
        System.out.println(newUsers.getEmail() + " " + newUsers.getPassword());
        String message = "";
        if(userRepository.findByEmail(newUsers.getEmail()) == null) {
            newUsers.setPassword(passwordEncoder.encode(newUsers.getPassword()));
            newUsers.setRole("USER");
            userRepository.save(newUsers);

            message = "User Saved Successfully";
        } else {
            message = "User Save Failed";
        }
        model.addAttribute("message" , message);
        return "User/Register";
    }
}
