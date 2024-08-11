package com.interfaces;

import com.projection.MessageProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface MessageServiceInterface {
    ResponseEntity<String> updateMessageById(String id, Map<String, Object> payload);

    Page<MessageProjection> getAllMessages(Integer itemsPerPage, Integer page);

    Page<MessageProjection> getAllMessagesWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);

    Optional<MessageProjection> getMessageById(String id);

    Optional<?> deteleMessageById(String id);

}