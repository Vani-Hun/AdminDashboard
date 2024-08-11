package com.mapper;

import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

//    public VideoDTO toDto(VideoEntity videoEntity) {
//        VideoDTO dto = new VideoDTO();
//        dto.setUserId(videoEntity.getUser().getId());
//        dto.setName(videoEntity.getName());
//        dto.setVideo(videoEntity.getVideo());
//        dto.setViews(videoEntity.getViews());
//        dto.setLikes(videoEntity.getLikes());
//        dto.setShareCount(videoEntity.getShareCount());
//        dto.setCaption(videoEntity.getCaption());
//        dto.setWho(videoEntity.getWho());
//        dto.setThumbnail(videoEntity.getThumbnail());
//        dto.setAllowComment(videoEntity.isAllowComment());
////        dto.setLikerIds(videoEntity.getLikers().stream().map(CustomerEntity::getId).collect(Collectors.toList()));
////        dto.setCommentIds(videoEntity.getComments().stream().map(CommentEntity::getId).collect(Collectors.toList()));
////        dto.setNotificationIds(videoEntity.getNotifications().stream().map(NotificationEntity::getId).collect(Collectors.toList()));
////        dto.setHashtagIds(videoEntity.getHashtags().stream().map(HashtagEntity::getId).collect(Collectors.toList()));
//        return dto;
//    }

//    public VideoEntity toEntity(VideoDTO videoDTO) {
//        VideoEntity entity = new VideoEntity();
//        CustomerEntity user = customerRepository.findById(videoDTO.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        entity.setUser(user);
//        entity.setName(videoDTO.getName());
//        entity.setVideo(videoDTO.getVideo());
//        entity.setViews(videoDTO.getViews());
//        entity.setLikes(videoDTO.getLikes());
//        entity.setShareCount(videoDTO.getShareCount());
//        entity.setCaption(videoDTO.getCaption());
//        entity.setWho(videoDTO.getWho());
//        entity.setThumbnail(videoDTO.getThumbnail());
//        entity.setAllowComment(videoDTO.isAllowComment());
//        List<CustomerEntity> likers = videoDTO.getLikerIds().stream()
//                .map(id -> customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found")))
//                .collect(Collectors.toList());
//        entity.setLikers(likers);
//        List<HashtagEntity> hashtags = videoDTO.getHashtagIds().stream()
//                .map(id -> hashtagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hashtag not found")))
//                .collect(Collectors.toList());
//        entity.setHashtags(hashtags);
//        return entity;
//    }
}
