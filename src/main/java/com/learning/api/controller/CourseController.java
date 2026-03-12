package com.learning.api.controller;

import com.learning.api.annotation.ApiController;
import com.learning.api.dto.CourseReq;
import com.learning.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ApiController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> sendCourses(@RequestBody CourseReq courseReq) {
        if (!courseService.sendCourses(courseReq)) {
            return ResponseEntity.status(400).body(Map.of("message", "建立失敗"));
        }
        return ResponseEntity.ok(Map.of("message", "ok"));
    }
}
