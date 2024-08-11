package com.dao;


import com.entity.MessageEntity;
import com.projection.MessageProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageDAO extends JpaRepository<MessageEntity, String> {
    Page<MessageEntity> findAll(Pageable pageable);

    Optional<MessageEntity> findById(String id);

    @Query(
            "SELECT m.id AS id, m.user_id AS user_id, m.text AS text, m.status AS status " +
                    "FROM MessageEntity m "
    )
    Page<MessageProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT m.id AS id, m.user_id AS user_id, m.text AS text, m.status AS status " +
                    "FROM MessageEntity m " +
                    "WHERE m.user_id LIKE %:searchValue% OR m.text LIKE %:searchValue% OR m.status LIKE %:searchValue%"
    )
    Page<MessageProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);

    @Query(
            "SELECT m.id AS id, m.user_id AS user_id, m.text AS text, m.status AS status " +
                    "FROM MessageEntity m " +
                    "WHERE m.id = :id"
    )
    Optional<MessageProjection> findByIdCommon(@Param("id") String id);

    @Query(
            "SELECT m.id AS id, m.user_id AS user_id, m.text AS text, m.status AS status, m.conversation_id AS conversation_id " +
                    "FROM MessageEntity m " +
                    "WHERE m.id = :id"
    )
    Optional<MessageProjection.MessageProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE MessageEntity m SET m.user_id = :user_id, m.text = :text, m.status = :status WHERE m.id = :id")
    void updateById(@Param("id") String id, @Param("user_id") String user_id, @Param("text") String text, @Param("status") String status);
}