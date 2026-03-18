package com.learning.api.service;

import com.learning.api.dto.CourseSearchDTO;
import com.learning.api.entity.Course;
import com.learning.api.entity.TutorSchedule;
import com.learning.api.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    public List<CourseSearchDTO> getAllCourseCards() {
        // 1. 撈出所有課程，並篩選出 isActive 為 true 的
        // 注意：根據你的 Entity，欄位名稱是 isActive
        List<Course> courses = courseRepo.findAll().stream()
                .filter(c -> c.getIsActive() != null && c.getIsActive())
                .collect(Collectors.toList());

        // 2. 轉換為 DTO
        return courses.stream().map(course -> {
            CourseSearchDTO dto = new CourseSearchDTO();
            dto.setId(course.getId());
            dto.setTutorId(course.getTutor().getId());
            dto.setTeacherName(course.getTutor().getUser().getName());
            dto.setAvatarUrl(course.getTutor().getAvatar());
            dto.setTitle(course.getTutor().getTitle());
            dto.setCourseName(course.getName());
            dto.setSubject(course.getSubject());
            dto.setDescription(course.getDescription());
            dto.setPrice(course.getPrice());

            // 🌟 3. 處理時段：對齊你的 TutorSchedule 欄位
            if (course.getTutor().getSchedules() != null) {
                List<String> slots = course.getTutor().getSchedules().stream()
                        .filter(TutorSchedule::getIsAvailable) // 只抓開放的時段
                        .map(this::convertToSlotTag)
                        .collect(Collectors.toList());
                dto.setAvailableSlots(slots);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // 🌟 依照你的 Entity 修正轉換邏輯
    private String convertToSlotTag(TutorSchedule s) {
        String period = "morning";
        int hour = s.getHour(); // 取得 9-21 的數字

        // 對齊你前端 explore.html 的定義：
        // morning: 09:00 - 13:00 / afternoon: 13:00 - 17:00 / evening: 17:00 - 21:00
        if (hour >= 13 && hour < 17) period = "afternoon";
        else if (hour >= 17) period = "evening";

        // 格式範例: "1-morning" (星期一上午)
        return s.getWeekday() + "-" + period;
    }
}