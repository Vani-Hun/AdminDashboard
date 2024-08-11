package com.projection;

import java.util.List;

public interface HashtagProjection {
    String getId();

    String getName();

    int getUsage();

    interface HashtagProjectionWithVideo extends HashtagProjection {
        List<VideoProjection> getVideos();
    }
}

