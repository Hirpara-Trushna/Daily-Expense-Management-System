package com.expense.dailyexpense.controller;

import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Show Login Page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Process Login
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        User user = userService.validateLogin(username, password);

        if (user == null) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        session.setAttribute("loggedInUser", user);

        return "redirect:/dashboard";
    }

    // Show Register Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Process Register
    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String email,
                                  Model model) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);

        boolean success = userService.registerUser(newUser);

        if (!success) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        model.addAttribute("success",
                "Registration successful. Please login.");

        return "login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }

    // Home Page
    @GetMapping("/")
    public String home() {

        return "redirect:/login";
    }
}