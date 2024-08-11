package com.services;

import com.dao.MessageDAO;
import com.interfaces.MessageServiceInterface;
import com.projection.MessageProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MessageService implements MessageServiceInterface {
    final private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public Page<MessageProjection> getAllMessages(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return messageDAO.findAllCommon(pageable);
    }

    @Override
    public Page<MessageProjection> getAllMessagesWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return messageDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<MessageProjection> getMessageById(String id) {
        return messageDAO.findByIdCommon(id);
    }

    @Override
    public Optional<?> deteleMessageById(String id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMessageById(String id, Map<String, Object> payload) {
        try {
            String user_id = (String) payload.get("user_id");
            String text = (String) payload.get("text");
            String status = (String) payload.get("status");

            messageDAO.updateById(id, user_id, text, status);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }
}
