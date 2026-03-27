package com.learning.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.learning.api.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ 原有方法
    List<Order> findByUserId(Long userId);
    List<Order> findByCourseId(Long courseId);
    List<Order> findByCourseIdIn(List<Long> courseIds);

    @Query("SELECT COALESCE(SUM(o.lessonCount - o.lessonUsed), 0) FROM Order o " +
            "WHERE o.userId = :userId AND o.courseId = :courseId AND o.status = 2")
    int sumRemainingLessons(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 🆕 修正：透過 courses 表查詢某老師的所有訂單
    @Query(value = "SELECT DISTINCT o.* FROM orders o " +
            "JOIN courses c ON o.course_id = c.id " +
            "WHERE c.tutor_id = :tutorId",
            nativeQuery = true)
    List<Order> findByTutorId(@Param("tutorId") Long tutorId);
}