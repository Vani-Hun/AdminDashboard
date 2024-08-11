package com.mapper;

import com.dto.NotificationDTO;
import com.entity.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDto(NotificationEntity notificationEntity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(notificationEntity.getUser().getId());
        dto.setInteractingUserId(notificationEntity.getInteractingUser().getId());
        dto.setVideoId(notificationEntity.getVideo() != null ? notificationEntity.getVideo().getId() : null);
        dto.setMessage(notificationEntity.getMessage());
        dto.setStatus(notificationEntity.isStatus());
        dto.setType(notificationEntity.getType());
        return dto;
    }

//    public NotificationEntity toEntity(NotificationDTO notificationDTO) {
//        NotificationEntity entity = new NotificationEntity();
//        CustomerEntity user = customerRepository.findById(notificationDTO.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        CustomerEntity interactingUser = customerRepository.findById(notificationDTO.getInteractingUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("Interacting user not found"));
//        VideoEntity video = notificationDTO.getVideoId() != null
//                ? videoRepository.findById(notificationDTO.getVideoId())
//                .orElseThrow(() -> new ResourceNotFoundException("Video not found"))
//                : null;
//        entity.setUser(user);
//        entity.setInteractingUser(interactingUser);
//        entity.setVideo(video);
//        entity.setMessage(notificationDTO.getMessage());
//        entity.setStatus(notificationDTO.isStatus());
//        entity.setType(notificationDTO.getType());
//        return entity;
//    }
}
