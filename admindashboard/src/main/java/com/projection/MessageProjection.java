package com.projection;

public interface MessageProjection {
    String getId();

    String getUser_id();

    String getText();

    String getStatus();

    interface MessageProjectionWithAll extends MessageProjection {
        ConversationProjection getConversation();
    }
}

