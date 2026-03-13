package com.learning.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.api.entity.Tutor;
import com.learning.api.repo.TutorRepository;
import com.learning.api.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class TutorProfileControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private Long savedTutorId;

    @BeforeEach
    void setUp() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // 建立老師 User (role=2)
        com.learning.api.entity.User tutor = new com.learning.api.entity.User();
        tutor.setName("Prof. Test");
        tutor.setEmail("tutorprofile@example.com");
        tutor.setPassword("hashedpassword");
        tutor.setRole(2);
        tutor.setWallet(0L);
        tutor = userRepository.save(tutor);
        savedTutorId = tutor.getId();
        // tutors 表不預先建立 — service 會自動 upsert
    }

    // ===================== PUT =====================

    @Test
    void put_validRequest_withAllFields_shouldReturn200() throws Exception {
        Map<String, Object> body = Map.of(
                "tutorId", savedTutorId,
                "name", "Updated Prof",
                "intro", "I have been teaching English for 10 years.",
                "certificate", "https://cert.example.com/1.pdf",
                "video", "https://video.example.com/intro.mp4"
        );

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(containsString("個人檔案儲存成功")));
    }

    @Test
    void put_missingTutorId_shouldReturn400() throws Exception {
        // Map.of 不接受 null，用 HashMap 省略 tutorId
        Map<String, Object> body = new HashMap<>();
        body.put("name", "No ID Prof");
        body.put("intro", "Some intro");

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("必須提供老師 ID"));
    }

    @Test
    void put_nonExistingTutorId_shouldReturn404() throws Exception {
        Map<String, Object> body = Map.of(
                "tutorId", 999999L,
                "name", "Nobody",
                "intro", "test"
        );

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value("更新失敗，找不到該名老師"));
    }

    @Test
    void put_withNameUpdate_shouldUpdateUserName() throws Exception {
        Map<String, Object> body = Map.of(
                "tutorId", savedTutorId,
                "name", "New Name",
                "intro", "intro text"
        );

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        String updatedName = userRepository.findById(savedTutorId).orElseThrow().getName();
        assertThat(updatedName).isEqualTo("New Name");
    }

    @Test
    void put_withoutName_shouldNotOverwriteUserName() throws Exception {
        // 不傳 name，只傳 intro
        Map<String, Object> body = Map.of(
                "tutorId", savedTutorId,
                "intro", "intro only"
        );

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        String name = userRepository.findById(savedTutorId).orElseThrow().getName();
        assertThat(name).isEqualTo("Prof. Test");
    }

    @Test
    void put_upsertsTutorRow_shouldCreateTutorRecord() throws Exception {
        assertThat(tutorRepository.existsById(savedTutorId)).isFalse();

        Map<String, Object> body = Map.of(
                "tutorId", savedTutorId,
                "intro", "Teaching since 2010"
        );

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        Tutor created = tutorRepository.findById(savedTutorId).orElseThrow();
        assertThat(created.getIntro()).isEqualTo("Teaching since 2010");
    }

    @Test
    void put_updatesExistingTutorRow_shouldOverwriteIntro() throws Exception {
        // 預先建立 tutors 列
        Tutor existing = new Tutor();
        existing.setId(savedTutorId);
        existing.setIntro("Old intro");
        tutorRepository.save(existing);

        Map<String, Object> body = Map.of(
                "tutorId", savedTutorId,
                "intro", "New intro"
        );

        mockMvc.perform(put("/api/teacher/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        Tutor updated = tutorRepository.findById(savedTutorId).orElseThrow();
        assertThat(updated.getIntro()).isEqualTo("New intro");
    }
}
