package com.learning.api.controller;

<<<<<<< HEAD
import com.learning.api.annotation.ApiController;
import com.learning.api.entity.TutorSchedule;
import com.learning.api.repo.TutorScheduleRepo;
=======
import com.learning.api.dto.ScheduleDTO;
>>>>>>> upstream/feature/Review
import com.learning.api.service.TutorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
@ApiController
=======
@CrossOrigin(origins = "*") // 允許前端跨域請求
@RestController
>>>>>>> upstream/feature/Review
@RequestMapping("/api/teacher/schedules")
@RequiredArgsConstructor
public class TutorScheduleController {

<<<<<<< HEAD
    private final TutorScheduleService scheduleService;
    private final TutorScheduleRepo scheduleRepo;

    // [POST] 老師新增一個空閒時段
    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody TutorSchedule schedule) {
        String result = scheduleService.addSchedule(schedule);
        if (!"success".equals(result)) {
            return ResponseEntity.status(400).body(Map.of("message", result));
        }
        return ResponseEntity.ok(Map.of("message", "排班成功！該時段已開放給家長預約。"));
    }

    // [GET] 取得某位老師所有的排班表
    @GetMapping("/{tutorId}")
    public ResponseEntity<?> getTutorSchedules(@PathVariable Long tutorId) {
        List<TutorSchedule> schedules = scheduleRepo.findByTutorId(tutorId);
        return ResponseEntity.ok(Map.of("tutorId", tutorId, "schedules", schedules));
=======
    @Autowired
    private TutorScheduleService scheduleService;

    // 1. 老師點擊格子切換狀態 (開放/關閉)
    // 呼叫範例: POST /api/teacher/schedules/toggle
    @PostMapping("/toggle")
    public ResponseEntity<?> toggleSlot(@RequestBody ScheduleDTO.ToggleReq req) {
        String result = scheduleService.toggleSchedule(req);

        if (!"success".equals(result)) {
            // 如果失敗 (例如時間格式錯誤)，回傳 400 錯誤與訊息
            return ResponseEntity.badRequest().body(Map.of("msg", result));
        }

        return ResponseEntity.ok(Map.of("msg", "時段狀態已更新"));
    }

    // 2. 獲取老師「常態性的一週課表」
    // 呼叫範例: GET /api/teacher/schedules/2
    @GetMapping("/{tutorId}")
    public ResponseEntity<?> getSchedule(@PathVariable Long tutorId) {
        List<ScheduleDTO.Res> schedules = scheduleService.getWeeklySchedule(tutorId);
        return ResponseEntity.ok(schedules);
>>>>>>> upstream/feature/Review
    }
}
