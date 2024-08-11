package com.interfaces;

import com.projection.CommentProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface CommentServiceInterface {
    ResponseEntity<String> updateCommentById(String id, Map<String, Object> payload);

    Page<CommentProjection> getAllComments(Integer itemsPerPage, Integer page);

    Page<CommentProjection> getAllCommentsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);


    Optional<CommentProjection> getCommentById(String id);

    void deteleCommentById(String id);
}