package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password) {

        User user = new User(name, email, password, "USER");
        userRepository.save(user);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        if(email.equals("admin@gmail.com")
           && password.equals("admin1234"))
        {
            session.setAttribute("admin", true);

            return "redirect:/admin-dashboard";
        }

        User user = userRepository.findByEmail(email);

        if (user != null &&
            user.getPassword().equals(password)) {

            session.setAttribute("loggedUser", user);

            return "redirect:/home";
        }

        else {

            model.addAttribute("error",
                               "Invalid email or password");

            return "login";
        }
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session,
                              Model model) {

        User user =
        (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session)
    {
        Boolean isAdmin =
        (Boolean) session.getAttribute("admin");

        if(isAdmin == null || !isAdmin)
        {
            return "redirect:/login";
        }

        return "admin-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}