package com.dao;

import com.entity.ConversationEntity;
import com.projection.ConversationProjection;
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
public interface ConversationDAO extends JpaRepository<ConversationEntity, String> {
    Page<ConversationEntity> findAll(Pageable pageable);

    Optional<ConversationEntity> findById(String id);

    @Query(
            "SELECT c.id AS id, c.count AS count " +
                    "FROM ConversationEntity c "
    )
    Page<ConversationProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT c.id AS id, c.count AS count " +
                    "FROM ConversationEntity c " +
                    "WHERE cast(c.count as string) LIKE %:searchValue%"
    )
    Page<ConversationProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);


    @Query(
            "SELECT c.id AS id, c.count AS count " +
                    "FROM ConversationEntity c " +
                    "WHERE c.id = :id"
    )
    Optional<ConversationProjection> findByIdCommon(@Param("id") String id);

    @Query(
            "SELECT c.id AS id, c.count AS count, c.admin_id AS admin_id, c.user_id AS user_id, c.participant_id AS participant_id, c.messages AS messages " +
                    "FROM ConversationEntity c " +
                    "WHERE c.id = :id"
    )
    Optional<ConversationProjection.ConversationProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE ConversationEntity c SET c.count = :count WHERE c.id = :id")
    void updateById(@Param("id") String id, @Param("count") Integer count);
}
