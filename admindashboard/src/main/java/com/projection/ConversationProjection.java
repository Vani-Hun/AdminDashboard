package com.projection;

import java.util.List;

public interface ConversationProjection {
    String getId();

    Integer getCount();


    interface ConversationProjectionWithAll extends ConversationProjection {
        AdminProjection getAdmin();

        CustomerProjection getUser();

        CustomerProjection getParticipant();

        List<MessageProjection> getMessages();
    }
}
