package com.services;

import com.dao.CommentDAO;
import com.interfaces.CommentServiceInterface;
import com.projection.CommentProjection;
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
public class CommentService implements CommentServiceInterface {
    final private CommentDAO commentDAO;

    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public Page<CommentProjection> getAllComments(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return commentDAO.findAllCommon(pageable);
    }

    @Override
    public Page<CommentProjection> getAllCommentsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return commentDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<CommentProjection> getCommentById(String id) {
        return commentDAO.findByIdCommon(id);
    }

    @Override
    @Transactional
    public void deteleCommentById(String id) {
        commentDAO.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateCommentById(String id, Map<String, Object> payload) {
        try {
            String text = (String) payload.get("text");
//            VideoEntity video = // convert payload to VideoEntity
//                    CustomerEntity customer = // convert payload to CustomerEntity

            commentDAO.updateById(id, text);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }
}
