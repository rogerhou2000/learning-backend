package com.learning.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.learning.api.entity.ChatMessage;
import com.learning.api.enums.MessageType;
import com.learning.api.repo.ChatMessageRepository;
<<<<<<< HEAD
import com.learning.api.repo.OrderRepository;
=======
import com.learning.api.repo.OrderRepo;
>>>>>>> upstream/feature/Review
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
<<<<<<< HEAD
    private final OrderRepository orderRepo;
=======
    private final OrderRepo orderRepo;
>>>>>>> upstream/feature/Review

    public List<ChatMessage> findByBookingId(Long bookingId) {
        return chatMessageRepository.findByBookingIdOrderByCreatedAtAsc(bookingId);
    }

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
<<<<<<< HEAD
            chatMessage.setMessage("");
=======
>>>>>>> upstream/feature/Review
        } else {
            chatMessage.setMessage(message);
        }

        return chatMessageRepository.save(chatMessage);
    }
<<<<<<< HEAD
    //fix
    public Optional<ChatMessage> update(Long id, String message) {
        return chatMessageRepository.findById(id).map(existing -> {
            if (message == null || message.trim().isEmpty()) {
                throw new IllegalArgumentException("消息內容不能為空");
            }
=======

    public Optional<ChatMessage> update(Long id, String message) {
        return chatMessageRepository.findById(id).map(existing -> {
/*             if (message == null || message.trim().isEmpty()) {
                throw new IllegalArgumentException("消息內容不能為空");
            } */
>>>>>>> upstream/feature/Review
            existing.setMessage(message);
            return chatMessageRepository.save(existing);
        });
    }

    public boolean deleteById(Long id) {
        if (chatMessageRepository.existsById(id)) {
            chatMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> upstream/feature/Review
