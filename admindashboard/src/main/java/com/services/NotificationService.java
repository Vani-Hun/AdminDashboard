package com.services;

import com.dao.NotificationDAO;
import com.interfaces.NotificationServiceInterface;
import com.projection.NotificationProjection;
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
public class NotificationService implements NotificationServiceInterface {
    final private NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public Page<NotificationProjection> getAllNotifications(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return notificationDAO.findAllCommon(pageable);
    }

    @Override
    public Page<NotificationProjection> getAllNotificationsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return notificationDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<NotificationProjection> getNotificationById(String id) {
        return notificationDAO.findByIdCommon(id);
    }

    @Override
    public Optional<?> deteleNotificationById(String id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateNotificationById(String id, Map<String, Object> payload) {
        try {
            String message = (String) payload.get("message");
            Boolean status = (Boolean) payload.get("status");
            String type = (String) payload.get("type");

            notificationDAO.updateById(id, message, status, type);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }
}
