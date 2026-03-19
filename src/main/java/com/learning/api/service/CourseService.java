package com.learning.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.api.dto.CourseDTO;
import com.learning.api.dto.CourseReq;
import com.learning.api.entity.Course;
import com.learning.api.entity.Tutor;
import com.learning.api.repo.CourseRepo;
import com.learning.api.repo.TutorRepo;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TutorRepo tutorRepo;

    // ── 查：所有課程 ──────────────────────────────────────────────────

<<<<<<< HEAD
        // check null
        if (courseReq.getTutorId() == null || courseReq.getName() == null ||
            courseReq.getSubject() == null  || courseReq.getLevel() == null ||
                courseReq.getPrice() == null || courseReq.getActive() == null) return false;

        if (courseReq.getName().trim().isEmpty()) {
            System.out.println("name is empty");
            return false;
        }

        // 有要設定這堂課不開就不能 post 嗎？ (暫時先：對）
        // if (!courseReq.isActive()) return false;

        // 先設定 1 塊就可開課 要改最低多少再改
        if (courseReq.getPrice() <= 0) {
            System.out.println("price is wrong");
            return false;
        }

        // 目前只有 1 英文科 2 程式語言科 不能 0 負數
        if (courseReq.getSubject() <= 0) return false;
        if (courseReq.getSubject()!=1 || courseReq.getSubject()!=2) return false;

        // level 1-5 不能 0 負數
        if (courseReq.getLevel() <= 0) return false;
        if (courseReq.getLevel() <1 || courseReq.getLevel() >5) return false;

        // member existsById
        User tutor = userRepo.findById(courseReq.getTutorId()).orElse(null);
        if ( tutor == null ) {
            System.out.println("tutor is null");
            return false;
        }

        // 只有老師可以新增課程
        if (tutor.getRole() == UserRole.TUTOR) {
            System.out.println("user isn't tutor");
            return false;
        }

        // buildCourses
        Course course = buildCourses(courseReq);
        courseRepo.save(course);

        // member existsById
        return true;
=======
    public List<CourseDTO> getCoursesByTutorId(Long tutorId) {
        validateTutorExists(tutorId);
        return courseRepo.findByTutorId(tutorId)
                .stream()
                .map(this::toDTO)
                .toList();
>>>>>>> feature/develop
    }

    // ── 查：單一課程 ──────────────────────────────────────────────────

    public CourseDTO getCourse(Long tutorId, Long courseId) {
        Course course = findCourseOrThrow(courseId);
        validateCourseOwnership(course, tutorId);
        return toDTO(course);
    }

    // ── 增 ────────────────────────────────────────────────────────────

    @Transactional
    public CourseDTO createCourse(Long tutorId, CourseReq dto) {
        Tutor tutor = tutorRepo.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("找不到老師 id=" + tutorId));

        Course course = new Course();
        course.setTutor(tutor);
        course.setName(dto.getName());
        course.setSubject(dto.getSubject());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setIsActive(dto.getActive() != null ? dto.getActive() : true);

        return toDTO(courseRepo.save(course));
    }
<<<<<<< HEAD
}
 */
//package com.learning.api.service;
//
//import com.learning.api.dto.*;
//import com.learning.api.entity.*;
//import com.learning.api.repo.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CourseService {
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private CourseRepo courseRepo;
//
//    // bookingReq.getUserId() -> 這是前端送 id
//    public boolean sendCourses(CourseReq courseReq){
//
//        if (courseReq == null) {
//            System.out.println("courseReq is null");
//            return false;
//        }
//
//        // check null
//        if (courseReq.getTutorId() == null || courseReq.getName() == null ||
//            courseReq.getSubject() == null  || courseReq.getLevel() == null ||
//                courseReq.getPrice() == null || courseReq.getActive() == null) return false;
//
//        if (courseReq.getName().trim().isEmpty()) {
//            System.out.println("name is empty");
//            return false;
//        }
//
//        // 有要設定這堂課不開就不能 post 嗎？ (暫時先：對）
//        // if (!courseReq.isActive()) return false;
//
//        // 先設定 1 塊就可開課 要改最低多少再改
//        if (courseReq.getPrice() <= 0) {
//            System.out.println("price is wrong");
//            return false;
//        }
//
//        // 目前只有 1 英文科 2 程式語言科 不能 0 負數
//        if (courseReq.getSubject() <= 0) return false;
//        if (courseReq.getSubject()!=1 || courseReq.getSubject()!=2) return false;
//
//        // level 1-5 不能 0 負數
//        if (courseReq.getLevel() <= 0) return false;
//        if (courseReq.getLevel() <1 || courseReq.getLevel() >5) return false;
//
//        // member existsById
//        User tutor = userRepo.findById(courseReq.getTutorId()).orElse(null);
//        if ( tutor == null ) {
//            System.out.println("tutor is null");
//            return false;
//        }
//
//        // 只有老師可以新增課程
//        if (tutor.getRole() != UserRole.TUTOR) {
//            System.out.println("user isn't tutor");
//            return false;
//        }
//
//        // buildCourses
//        Course course = buildCourses(courseReq);
//        courseRepo.save(course);
//
//        // member existsById
//        return true;
//    }
//
//    private Course buildCourses(CourseReq courseReq){
//
//        Course course = new Course();
//        //set
//        course.setTutorId(courseReq.getTutorId());
//        course.setName(courseReq.getName().trim());
//        course.setSubject(courseReq.getSubject());
//        course.setDescription(courseReq.getDescription());
//        course.setPrice(courseReq.getPrice());
//        course.setActive(courseReq.getActive());
//
//        return course;
//    }
//}
=======

    // ── 修 ────────────────────────────────────────────────────────────

    @Transactional
    public CourseDTO updateCourse(Long tutorId, Long courseId, CourseReq dto) {
        Course course = findCourseOrThrow(courseId);
        validateCourseOwnership(course, tutorId);

        if (dto.getName()        != null) course.setName(dto.getName());
        if (dto.getSubject()     != null) course.setSubject(dto.getSubject());
        if (dto.getDescription() != null) course.setDescription(dto.getDescription());
        if (dto.getPrice()       != null) course.setPrice(dto.getPrice());
        if (dto.getActive()      != null) course.setIsActive(dto.getActive());

        return toDTO(courseRepo.save(course));
    }

    // ── 刪 ────────────────────────────────────────────────────────────

    @Transactional
    public void deleteCourse(Long tutorId, Long courseId) {
        Course course = findCourseOrThrow(courseId);
        validateCourseOwnership(course, tutorId);
        courseRepo.delete(course);
    }

    // ── 私有輔助方法 ──────────────────────────────────────────────────

    /** Entity → DTO，只取純資料欄位，切斷所有 entity 關聯 */
    private CourseDTO toDTO(Course course) {
        return new CourseDTO(
            course.getId(),
            course.getName(),
            course.getSubject(),
            course.getDescription(),
            course.getPrice(),
            course.getIsActive()
        );
    }

    private void validateTutorExists(Long tutorId) {
        if (!tutorRepo.existsById(tutorId)) {
            throw new RuntimeException("找不到老師 id=" + tutorId);
        }
    }

    private Course findCourseOrThrow(Long courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("找不到課程 id=" + courseId));
    }

    private void validateCourseOwnership(Course course, Long tutorId) {
        if (!course.getTutor().getId().equals(tutorId)) {
            throw new SecurityException("此課程不屬於老師 id=" + tutorId);
        }
    }
}
>>>>>>> feature/develop
