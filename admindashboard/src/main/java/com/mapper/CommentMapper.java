package com.mapper;

import com.dto.CommentDTO;
import com.entity.CommentEntity;

public class CommentMapper {

    public static CommentDTO toDto(CommentEntity commentEntity) {
        CommentDTO dto = new CommentDTO();
        dto.setText(commentEntity.getText());
        dto.setVideoId(commentEntity.getVideo().getId());
        dto.setCustomerId(commentEntity.getCustomer().getId());
        return dto;
    }

//    public static CommentEntity toEntity(CommentDTO commentDTO, VideoEntity video, CustomerEntity customer) {
//        CommentEntity entity = new CommentEntity();
//        entity.setText(commentDTO.getText());
//        entity.setVideo(video);
//        entity.setCustomer(customer);
//        return entity;
//    }
}
