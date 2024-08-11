package com.projection;

import java.util.List;

public interface VideoProjection {
    String getId();

    String getName();

    String getVideo();

    Integer getViews();

    Integer getLikes();

    Integer getShare_count();

    String getCaption();

    String getWho();

    String getThumbnail();

    Boolean getAllow_comment();


    interface VideoProjectionWithAll extends VideoProjection {
        CustomerProjection getUser();

        List<CustomerProjection> getLikers();

        List<CommentProjection> getComments();

        List<NotificationProjection> getNotifications();

        List<HashtagProjection> getHashtags();

    }


}
