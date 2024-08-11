package com.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface HashtagServiceInterface {
    ResponseEntity<String> updateHashtagById(String id, Map<String, Object> payload);

    Page<?> getAllHashtags(Integer itemsPerPage, Integer page);

    Page<?> getAllHashtagsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);


    Optional<?> getHashtagById(String id, boolean video);

    ResponseEntity<?> createHashtag(Map<String, ?> payload);

    Optional<?> deteleHashtagById(String id);

}

