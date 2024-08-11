package com.dao;

import com.entity.HashtagEntity;
import com.projection.HashtagProjection;
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
public interface HashtagDAO extends JpaRepository<HashtagEntity, String> {

    Page<HashtagEntity> findAll(Pageable pageable);

    @Query("SELECT h.id AS id, h.name AS name, h.usage AS usage " +
            "FROM HashtagEntity h ")
    Page<HashtagProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT h.id AS id, h.name AS name, h.usage AS usage " +
                    "FROM HashtagEntity h " +
                    "WHERE h.name LIKE %:searchValue% OR cast(h.usage as string) LIKE %:searchValue%"
    )
    Page<HashtagProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);


    @Query("SELECT h.id AS id, h.name AS name, h.usage AS usage " +
            "FROM HashtagEntity h " +
            "WHERE h.id = :id")
    Optional<HashtagProjection> findByIdCommon(@Param("id") String id);

    @Query("SELECT h.id AS id, h.name AS name, h.usage AS usage, h.videos AS videos " +
            "FROM HashtagEntity h " +
            "WHERE h.id = :id")
    Optional<HashtagProjection.HashtagProjectionWithVideo> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE HashtagEntity h SET h.name = :name,h.usage = :usage WHERE h.id = :id")
    void updateById(@Param("id") String id, @Param("name") String name, @Param("usage") Integer usage);
}