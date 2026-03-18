package com.learning.api.dto;

import lombok.Data;
import java.time.Instant;

/**
 * 視訊聊天室事件 DTO
 * type: "joined" | "left"
 */
@Data
public class RoomEvent {
    private String type;        // joined / left
    private String role;        // student / tutor
    private Instant timestamp = Instant.now();
}
