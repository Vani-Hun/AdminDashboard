package com.services;

import com.dao.HashtagDAO;
import com.entity.HashtagEntity;
import com.interfaces.HashtagServiceInterface;
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
public class HashtagService implements HashtagServiceInterface {
    final private HashtagDAO hashtagDAO;

    public HashtagService(HashtagDAO hashtagDAO) {
        this.hashtagDAO = hashtagDAO;
    }

    @Override
    public Page<?> getAllHashtags(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return hashtagDAO.findAllCommon(pageable);
    }

    @Override
    public Page<?> getAllHashtagsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return hashtagDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<?> getHashtagById(String id, boolean video) {
        if (video) {
            return hashtagDAO.findByIdWithAll(id);
        } else {
            return hashtagDAO.findByIdCommon(id);
        }
    }

    @Override
    public ResponseEntity<?> createHashtag(Map<String, ?> payload) {
        try {
            HashtagEntity hashtagEntity = new HashtagEntity();
            hashtagEntity.setName((String) payload.get("name"));
            hashtagEntity.setUsage((Integer) payload.get("usage"));
            HashtagEntity savedEntity = hashtagDAO.save(hashtagEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Hashtag created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public Optional<?> deteleHashtagById(String id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateHashtagById(String id, Map<String, Object> payload) {
        try {
            String name = (String) payload.get("name");
            Integer usage = (Integer) payload.get("usage");
            hashtagDAO.updateById(id, name, usage);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }
}
