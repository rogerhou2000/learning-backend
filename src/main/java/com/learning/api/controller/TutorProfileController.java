package com.learning.api.controller;

import com.learning.api.annotation.ApiController;
import com.learning.api.dto.TutorProfileDTO;
import com.learning.api.service.TutorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ApiController
@RequestMapping("/api/teacher/profile")
@RequiredArgsConstructor
public class TutorProfileController {

    private final TutorProfileService profileService;

    // [PUT] 更新老師個人檔案
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody TutorProfileDTO dto) {
        if (dto.getTutorId() == null) {
            return ResponseEntity.status(400).body(Map.of("message", "必須提供老師 ID"));
        }
        if (!profileService.updateProfile(dto)) {
            return ResponseEntity.status(404).body(Map.of("message", "更新失敗，找不到該名老師"));
        }
        return ResponseEntity.ok(Map.of("message", "個人檔案儲存成功！您的學生現在可以看到最新資訊了！"));
    }
}
