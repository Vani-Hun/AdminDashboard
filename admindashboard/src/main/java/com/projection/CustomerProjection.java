package com.projection;

import com.entity.ConversationEntity;

import java.util.List;

public interface CustomerProjection {
    String getId();

    String getLogo();

    String getName();

    String getUsername();

    String getEmail();

    String getPhone();

    String getBio();

    String getMessage_status();

    Boolean getVideo_liked_status();

    String getPermission();

    Integer getLikes();


    interface CustomerProjectionWithAll extends CustomerProjection {
        List<ConversationProjection> getUserConversations();

        List<ConversationEntity> getParticipantConversations();

        List<CustomerProjection> getFollowers();

        List<CustomerProjection> getFollowing();

        List<VideoProjection> getVideos();

        List<NotificationProjection> getNotifications();

        List<CommentProjection> getComments();

        List<VideoProjection> getLikedVideos();
    }


}
