package com.mapper;

import com.dao.CustomerDAO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final CustomerDAO customerDAO;

    public CustomerMapper(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

//    public static CustomerEntity toEntity(CustomerDTO customerDTO, List<ConversationEntity> conversations, List<CustomerEntity> followers, List<CustomerEntity> following, List<VideoEntity> videos, List<NotificationEntity> notifications, List<CommentEntity> comments, List<VideoEntity> likedVideos) {
//        CustomerEntity entity = new CustomerEntity();
//        entity.setLogo(customerDTO.getLogo());
//        entity.setName(customerDTO.getName());
//        entity.setUsername(customerDTO.getUsername());
//        entity.setEmail(customerDTO.getEmail());
//        entity.setPhone(customerDTO.getPhone());
//        entity.setBio(customerDTO.getBio());
//        entity.setPassword(customerDTO.getPassword());
//        entity.setMessageStatus(customerDTO.getMessageStatus());
//        entity.setConversations(conversations);
//        entity.setPermission(customerDTO.getPermission());
//        entity.setFollowers(followers);
//        entity.setFollowing(following);
//        entity.setLikes(customerDTO.getLikes());
//        entity.setVideos(videos);
//        entity.setNotifications(notifications);
//        entity.setComments(comments);
//        entity.setLikedVideos(likedVideos);
//        return entity;
//    }

//    public CustomerDTO toDto(CustomerEntity customerEntity) {
//        CustomerDTO dto = new CustomerDTO();
//        dto.setLogo(customerEntity.getLogo());
//        dto.setName(customerEntity.getName());
//        dto.setUsername(customerEntity.getUsername());
//        dto.setEmail(customerEntity.getEmail());
//        dto.setPhone(customerEntity.getPhone());
//        dto.setBio(customerEntity.getBio());
//        dto.setPassword(customerEntity.getPassword());
//        dto.setMessageStatus(customerEntity.getMessageStatus());
////        dto.setConversationIds(customerEntity.getUserConversations().stream()
////                .map(ConversationEntity::getId)
////                .collect(Collectors.toList()));
////        dto.setConversationIds(customerEntity.getParticipantConversations().stream()
////                .map(ConversationEntity::getId)
////                .collect(Collectors.toList()));
////        dto.setPermission(customerEntity.getPermission());
////        dto.setFollowerIds(customerEntity.getFollowers().stream()
////                .map(CustomerEntity::getId)
////                .collect(Collectors.toList()));
////        dto.setFollowingIds(customerEntity.getFollowing().stream()
////                .map(CustomerEntity::getId)
////                .collect(Collectors.toList()));
////        dto.setLikes(customerEntity.getLikes());
//////        dto.setVideos(customerEntity.getVideos().stream()
//////                .map(VideoEntity::getId)
//////                .collect(Collectors.toSet()));
////        dto.setNotificationIds(customerEntity.getNotifications().stream()
////                .map(NotificationEntity::getId)
////                .collect(Collectors.toList()));
////        dto.setCommentIds(customerEntity.getComments().stream()
////                .map(CommentEntity::getId)
////                .collect(Collectors.toList()));
////        dto.setLikedVideoIds(customerEntity.getLikedVideos().stream()
////                .map(VideoEntity::getId)
////                .collect(Collectors.toList()));
//        return dto;
//    }


}
