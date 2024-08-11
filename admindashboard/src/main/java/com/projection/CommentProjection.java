package com.projection;

public interface CommentProjection {
    String getId();

    String getText();


    interface CommentProjectionWithAll extends CommentProjection {
        VideoProjection getVideo();

        CustomerProjection getCustomer();
    }
}

