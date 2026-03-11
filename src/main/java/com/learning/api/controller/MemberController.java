package com.learning.api.controller;

import com.learning.api.entity.User;
import com.learning.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class MemberController {

    @Autowired
    private UserService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){

        if (!memberService.register(user)) {
            return ResponseEntity.status(400).body(Map.of("msg", "註冊失敗"));
        }

        return ResponseEntity.ok(Map.of("msg", "歡迎"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User member){

        if (!memberService.login(member.getEmail(), member.getPassword())){
            return ResponseEntity.status(401).body(Map.of("msg", "帳號或密碼錯誤"));
        }

        return ResponseEntity.ok(Map.of("msg", "歡迎"));
    }
}
