package com.interfaces;

import com.projection.ConversationProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface ConversationServiceInterface {
    ResponseEntity<String> updateConversationById(String id, Map<String, Object> payload);

    Page<ConversationProjection> getAllConversations(Integer itemsPerPage, Integer page);

    Page<ConversationProjection> getAllConversationsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);

    Optional<ConversationProjection> getConversationById(String id);

    Optional<?> deteleConversationById(String id);

}
