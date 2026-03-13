package com.learning.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.learning.api.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
<<<<<<< HEAD
    List<Order> findByCourseId(Long courseId);
=======
    List<Order> findByUserId(Long userId);
    @Query("SELECT COALESCE(SUM(o.lessonCount - o.lessonUsed), 0) FROM Order o " +
       "WHERE o.userId = :userId AND o.courseId = :courseId AND o.status = 2")
    int sumRemainingLessons(@Param("userId") Long userId, @Param("courseId") Long courseId);
>>>>>>> 057704559886e802faa1eb5122deeb7c5f261e7a
}
