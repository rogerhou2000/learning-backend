//package com.learning.api.controller;
//
//import com.learning.api.dto.CourseSearchDTO;
//import com.learning.api.service.CourseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/api/courses")
//public class CourseController {
//
//    @Autowired
//    private CourseService courseService;
//
//    @GetMapping
//    public ResponseEntity<List<CourseSearchDTO>> getAllCourses() {
//        List<CourseSearchDTO> courses = courseService.getAllCourseCards();
//        return ResponseEntity.ok(courses);
//    }
//}