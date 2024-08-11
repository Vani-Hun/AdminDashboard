package com.dao;

import com.entity.CustomerEntity;
import com.projection.CustomerProjection;
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
public interface CustomerDAO extends JpaRepository<CustomerEntity, String> {
    Page<CustomerEntity> findAll(Pageable pageable);

    Optional<CustomerEntity> findByUsername(String username);

    Optional<CustomerEntity> findById(String id);

    @Query(
            "SELECT c.id AS id, c.logo AS logo, c.name AS name, c.username AS username, c.email AS email, c.phone AS phone, c.bio AS bio, c.message_status AS message_status,c.video_liked_status AS video_liked_status, c.permission AS permission, c.likes AS likes " +
                    "FROM CustomerEntity c "
    )
    Page<CustomerProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT c.id AS id, c.logo AS logo, c.name AS name, c.username AS username, c.email AS email, c.phone AS phone, c.bio AS bio, c.message_status AS message_status,c.video_liked_status AS video_liked_status, c.permission AS permission, c.likes AS likes " +
                    "FROM CustomerEntity c " +
                    "WHERE c.name LIKE %:searchValue% OR c.username LIKE %:searchValue% OR c.email LIKE %:searchValue% OR c.phone LIKE %:searchValue%"
    )
    Page<CustomerProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);

    @Query(
            "SELECT c.id AS id, c.logo AS logo, c.name AS name, c.username AS username, c.email AS email, c.phone AS phone, c.bio AS bio, c.message_status AS message_status,c.video_liked_status AS video_liked_status, c.permission AS permission, c.likes AS likes " +
                    "FROM CustomerEntity c " +
                    "WHERE c.id = :id"
    )
    Optional<CustomerProjection> findByIdCommon(@Param("id") String id);

    @Query(
            "SELECT c.id AS id, c.logo AS logo, c.name AS name, c.username AS username, c.email AS email, c.phone AS phone, c.bio AS bio, c.message_status AS message_status,c.video_liked_status AS video_liked_status, c.permission AS permission, c.likes AS likes, " +
                    "c.following AS following, c.followers AS followers, c.user_conversations AS user_conversations , c.participant_conversations AS participant_conversations, c.videos AS videos, c.notifications AS notifications, c.liked_videos AS liked_videos   " +
                    "FROM CustomerEntity c " +
                    "WHERE c.id = :id"
    )
    Optional<CustomerProjection.CustomerProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE CustomerEntity c SET c.logo = :logo,c.name = :name,c.username = :username,c.email = :email,c.phone = :phone,c.bio = :bio,c.message_status = :message_status,c.video_liked_status = :video_liked_status,c.permission = :permission,c.likes = :likes WHERE c.id = :id")
    void updateById(@Param("id") String id, @Param("logo") String logo, @Param("name") String name, @Param("username") String username, @Param("email") String email, @Param("phone") Integer phone, @Param("bio") String bio, @Param("message_status") String message_status, @Param("video_liked_status") Boolean video_liked_status, @Param("permission") String permission, @Param("likes") Integer likes);
}
