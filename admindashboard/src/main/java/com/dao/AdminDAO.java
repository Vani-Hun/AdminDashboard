package com.dao;

import com.entity.AdminEntity;
import com.projection.AdminProjection;
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
public interface AdminDAO extends JpaRepository<AdminEntity, String> {
    Page<AdminEntity> findAll(Pageable pageable);

    Optional<AdminEntity> findByUsername(String username);

    Optional<AdminEntity> findById(String id);

    @Query(
            "SELECT a.id AS id, a.name AS name, a.username AS username, a.logo AS logo, a.permission AS permission " +
                    "FROM AdminEntity a "
    )
    Page<AdminProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT a.id AS id, a.name AS name, a.username AS username, a.logo AS logo, a.permission AS permission " +
                    "FROM AdminEntity a " +
                    "WHERE a.name LIKE %:searchValue% " +
                    "OR a.username LIKE %:searchValue% " +
                    "OR a.permission LIKE %:searchValue%"
    )
    Page<AdminProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);

    @Query(
            "SELECT a.id AS id, a.name AS name, a.username AS username, a.logo AS logo, a.permission AS permission " +
                    "FROM AdminEntity a " +
                    "WHERE a.id = :id"
    )
    Optional<AdminProjection> findByIdCommon(@Param("id") String id);

    @Query(
            "SELECT a.id AS id, a.name AS name, a.username AS username, a.logo AS logo, a.permission AS permission " +
                    "FROM AdminEntity a " +
                    "WHERE a.id = :id"
    )
    Optional<AdminProjection.AdminProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("DELETE FROM AdminEntity a WHERE a.id = :id")
    void deleteById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE AdminEntity a SET a.username = :username,a.name = :name,a.permission = :permission,a.logo = :logo WHERE a.id = :id")
    void updateById(@Param("id") String id, @Param("username") String username, @Param("name") String name, @Param("permission") String permission, @Param("logo") String logo);
}