package com.mapper;

import com.dto.MessageDTO;
import com.entity.ConversationEntity;
import com.entity.MessageEntity;

public class MessageMapper {

    public static MessageDTO toDto(MessageEntity messageEntity) {
        MessageDTO dto = new MessageDTO();
        dto.setConversationId(messageEntity.getConversation().getId());
        dto.setUserId(messageEntity.getUserId());
        dto.setText(messageEntity.getText());
        dto.setStatus(messageEntity.getStatus());
        return dto;
    }

    public static MessageEntity toEntity(MessageDTO messageDTO, ConversationEntity conversation) {
        MessageEntity entity = new MessageEntity();
        entity.setConversation(conversation);
        entity.setUserId(messageDTO.getUserId());
        entity.setText(messageDTO.getText());
        entity.setStatus(messageDTO.getStatus());
        return entity;
    }
}
