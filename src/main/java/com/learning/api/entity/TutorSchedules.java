package com.learning.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tutor_schedules")
@Data
public class TutorSchedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tutor_id", nullable = false)
    private Long tutorId;

    @Column(nullable = false)
    private Byte weekday;

    @Column(nullable = false)
    private Byte hour;
}