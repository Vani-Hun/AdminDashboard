package com.interfaces;

import com.projection.NotificationProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface NotificationServiceInterface {
    ResponseEntity<String> updateNotificationById(String id, Map<String, Object> payload);

    Page<NotificationProjection> getAllNotifications(Integer itemsPerPage, Integer page);

    Page<NotificationProjection> getAllNotificationsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);

    Optional<NotificationProjection> getNotificationById(String id);

    Optional<?> deteleNotificationById(String id);

}
