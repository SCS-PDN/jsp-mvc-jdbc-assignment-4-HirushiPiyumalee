package com.example.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/courses")
    public String showCourses(Model model, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        String sql = "SELECT * FROM courses";
        List<Map<String, Object>> courses = jdbcTemplate.queryForList(sql);

        model.addAttribute("courses", courses);

        return "courses";
    }

    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable int courseId, HttpSession session) {

        String user = (String) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // Get student ID
        String studentSql = "SELECT student_id FROM students WHERE email=?";
        int studentId = jdbcTemplate.queryForObject(studentSql, Integer.class, user);

        // Check duplicate
        String checkSql = "SELECT COUNT(*) FROM registrations WHERE student_id=? AND course_id=?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, studentId, courseId);

        if (count == 0) {
            String insertSql = "INSERT INTO registrations(student_id, course_id, date) VALUES (?, ?, NOW())";
            jdbcTemplate.update(insertSql, studentId, courseId);
        }

        System.out.println("User " + user + " registered course " + courseId);

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}