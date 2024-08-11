package com.dao;


import com.entity.NotificationEntity;
import com.projection.NotificationProjection;
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
public interface NotificationDAO extends JpaRepository<NotificationEntity, String> {
    Page<NotificationEntity> findAll(Pageable pageable);

    Optional<NotificationEntity> findById(String id);

    @Query(
            "SELECT n.id AS id, n.message AS message, n.status AS status, n.type AS type " +
                    "FROM NotificationEntity n "
    )
    Page<NotificationProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT n.id AS id, n.message AS message, n.status AS status, n.type AS type " +
                    "FROM NotificationEntity n " +
                    "WHERE n.message LIKE %:searchValue% OR cast(n.status as string) LIKE %:searchValue% OR n.type LIKE %:searchValue%"
    )
    Page<NotificationProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);

    @Query(
            "SELECT n.id AS id, n.message AS message, n.status AS status, n.type AS type " +
                    "FROM NotificationEntity n " +
                    "WHERE n.id = :id"
    )
    Optional<NotificationProjection> findByIdCommon(@Param("id") String id);

    @Query(
            "SELECT n.id AS id, n.message AS message, n.status AS status, n.type AS type, n.user AS user, n.interacting_user AS interacting_user, n.video AS video " +
                    "FROM NotificationEntity n " +
                    "WHERE n.id = :id"
    )
    Optional<NotificationProjection.NotificationProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationEntity n SET n.message = :message, n.status = :status, n.type = :type WHERE n.id = :id")
    void updateById(@Param("id") String id, @Param("message") String message, @Param("status") Boolean status, @Param("type") String type);
}