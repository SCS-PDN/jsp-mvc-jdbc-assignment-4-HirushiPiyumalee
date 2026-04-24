package com.example.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            org.springframework.ui.Model model
    ) {

        String sql = "SELECT * FROM students WHERE email=? AND password=?";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, email, password);

        if (!users.isEmpty()) {
            session.setAttribute("user", email);
            System.out.println("Login SUCCESS: " + email);
            return "redirect:/courses";
        } else {
            System.out.println("Login FAILED: " + email);
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}