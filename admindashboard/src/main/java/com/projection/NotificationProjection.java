package com.projection;

import com.constants.NotificationType;

public interface NotificationProjection {
    String getId();

    String getMessage();

    Boolean getStatus();

    interface NotificationProjectionWithAll extends NotificationProjection {
        NotificationType getType();

        CustomerProjection getUser();

        CustomerProjection getInteractingUser();

        VideoProjection getVideo();
    }
}

