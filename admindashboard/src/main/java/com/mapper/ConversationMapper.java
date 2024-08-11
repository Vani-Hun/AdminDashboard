package com.mapper;

import com.dto.ConversationDTO;
import com.entity.ConversationEntity;
import com.entity.MessageEntity;

import java.util.stream.Collectors;

public class ConversationMapper {

    public static ConversationDTO toDto(ConversationEntity conversationEntity) {
        ConversationDTO dto = new ConversationDTO();
        dto.setCount(conversationEntity.getCount());
        dto.setAdminId(conversationEntity.getAdmin().getId());
        dto.setUserId(conversationEntity.getUser().getId());
        dto.setParticipantId(conversationEntity.getParticipant().getId());
        dto.setMessageIds(conversationEntity.getMessages().stream()
                .map(MessageEntity::getId)
                .collect(Collectors.toList()));
        return dto;
    }

//    public static ConversationEntity toEntity(ConversationDTO conversationDTO, AdminEntity admin, CustomerEntity user, CustomerEntity participant, Set<MessageEntity> messages) {
//        ConversationEntity entity = new ConversationEntity();
//        entity.setAdmin(admin);
//        entity.setUser(user);
//        entity.setParticipant(participant);
//        entity.setMessages(messages);
//        return entity;
//    }
}
