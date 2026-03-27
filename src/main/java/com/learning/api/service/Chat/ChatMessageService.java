package com.learning.api.service.Chat;

import com.learning.api.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.learning.api.dto.ChatRoom.ConversationDTO;
import com.learning.api.entity.ChatMessage;
import com.learning.api.entity.Order;
import com.learning.api.enums.MessageType;
import com.learning.api.repo.UserRepo;
import com.learning.api.repo.CourseRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final OrderRepository orderRepo;
    private final UserRepo userRepo;
    private final CourseRepo courseRepo;

    // ✅ 原有方法：查詢單一預約的訊息
    public List<ChatMessage> findByBookingId(Long bookingId) {
        return chatMessageRepository.findByBookingIdOrderByCreatedAtAsc(bookingId);
    }

    // 🆕 新增：查詢多個預約的訊息（合併）
    public List<ChatMessage> findByOrderIds(List<Long> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return List.of();
        }
        return chatMessageRepository.findByOrderIdsOrderByCreatedAtAsc(orderIds);
    }

    // 🆕 新增：查詢對話列表（按學生分組）
    public List<ConversationDTO> findConversationsByTutorId(Long tutorId) {
        // 1. 透過 booking_records 查詢該 tutor 的所有訂單
        List<Order> orders = orderRepo.findByTutorId(tutorId);

        if (orders.isEmpty()) {
            return List.of();
        }

        // 2. 按 userId (studentId) 分組
        Map<Long, List<Order>> groupedByStudent = orders.stream()
                .collect(Collectors.groupingBy(Order::getUserId));

        // 3. 對每個學生，組裝 ConversationDTO
        List<ConversationDTO> conversations = new ArrayList<>();

        for (Map.Entry<Long, List<Order>> entry : groupedByStudent.entrySet()) {
            Long studentId = entry.getKey();
            List<Order> studentOrders = entry.getValue();

            // 取得該學生的所有 orderId
            List<Long> orderIds = studentOrders.stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());

            // 查詢學生資訊
            var studentOpt = userRepo.findById(studentId);
            if (studentOpt.isEmpty()) {
                continue; // 學生不存在，跳過
            }

            var student = studentOpt.get();

            // 取得課程名稱列表
            List<Long> courseIds = studentOrders.stream()
                    .map(Order::getCourseId)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> courseNames = new ArrayList<>();
            for (Long courseId : courseIds) {
                var courseOpt = courseRepo.findById(courseId);
                if (courseOpt.isPresent()) {
                    courseNames.add(courseOpt.get().getName());
                }
            }

            // 查詢最後一則訊息
            Optional<ChatMessage> lastMsg = chatMessageRepository.findLastMessageByOrderIds(orderIds);

            // ✅ 修正：不使用 getAvatar()，改用預設頭貼或空字串
            ConversationDTO conversation = ConversationDTO.builder()
                    .studentId(studentId)
                    .studentName(student.getName())
                    .studentAvatar("")  // ✅ 改為空字串，讓前端使用預設頭貼
                    .orderIds(orderIds)
                    .courses(courseNames)
                    .lastMessage(lastMsg.map(ChatMessage::getMessage).orElse(""))
                    .lastMessageTime(lastMsg.map(ChatMessage::getCreatedAt).orElse(null))
                    .unreadCount(0)
                    .build();

            conversations.add(conversation);
        }

        // 4. 依最後訊息時間排序（最新的在前）
        conversations.sort((a, b) -> {
            if (a.getLastMessageTime() == null) return 1;
            if (b.getLastMessageTime() == null) return -1;
            return b.getLastMessageTime().compareTo(a.getLastMessageTime());
        });

        return conversations;
    }

    // ✅ 原有方法：儲存訊息
    public ChatMessage save(Long bookingId, Integer role, Integer messageTypeValue, String message, String mediaUrl) {
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException("Booking ID 不能為空");
        }

        orderRepo.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking ID: " + bookingId + " 不存在"));

        MessageType type = MessageType.fromValue(messageTypeValue != null ? messageTypeValue : MessageType.TEXT.getValue());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setOrderId(bookingId);
        chatMessage.setRole(role);
        chatMessage.setMessageType(type.getValue());

        if (type.isMedia()) {
            chatMessage.setMediaUrl(mediaUrl);
            if (message != null && !message.isBlank()) {
                chatMessage.setMessage(message);
            }
        } else {
            chatMessage.setMessage(message);
        }

        return chatMessageRepository.save(chatMessage);
    }

    // ✅ 原有方法：更新訊息
    public Optional<ChatMessage> update(Long id, String message) {
        return chatMessageRepository.findById(id).map(existing -> {
            existing.setMessage(message);
            return chatMessageRepository.save(existing);
        });
    }

    // ✅ 原有方法：刪除訊息
    public boolean deleteById(Long id) {
        if (chatMessageRepository.existsById(id)) {
            chatMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}