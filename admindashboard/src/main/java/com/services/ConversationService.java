package com.services;

import com.dao.ConversationDAO;
import com.interfaces.ConversationServiceInterface;
import com.projection.ConversationProjection;
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
public class ConversationService implements ConversationServiceInterface {
    final private ConversationDAO conversationDAO;

    public ConversationService(ConversationDAO conversationDAO) {
        this.conversationDAO = conversationDAO;
    }

    @Override
    public Page<ConversationProjection> getAllConversations(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return conversationDAO.findAllCommon(pageable);
    }

    @Override
    public Page<ConversationProjection> getAllConversationsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return conversationDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<ConversationProjection> getConversationById(String id) {
        return conversationDAO.findByIdCommon(id);
    }

    @Override
    public Optional<?> deteleConversationById(String id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateConversationById(String id, Map<String, Object> payload) {
        try {
            Integer count = (Integer) payload.get("count");
            conversationDAO.updateById(id, count);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }
}
