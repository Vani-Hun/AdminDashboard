package com.dao;

import com.dto.StatsDTO;
import com.entity.VideoEntity;
import com.projection.VideoProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoDAO extends JpaRepository<VideoEntity, String> {
    Page<VideoEntity> findAll(Pageable pageable);

    Optional<VideoEntity> findById(String id);

    @Query(
            "SELECT v.id AS id, v.name AS name, v.video AS video, v.views AS views, v.share_count AS share_count, v.caption AS caption, v.who AS who, v.thumbnail AS thumbnail, v.allow_comment AS allow_comment, v.likes AS likes  " +
                    "FROM VideoEntity v"
    )
    Page<VideoProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT v.id AS id, v.name AS name, v.video AS video, v.views AS views, v.share_count AS share_count, v.caption AS caption, v.who AS who, v.thumbnail AS thumbnail, v.allow_comment AS allow_comment, v.likes AS likes " +
                    "FROM VideoEntity v " +
                    "WHERE v.name LIKE %:searchValue% OR v.caption LIKE %:searchValue% OR v.who LIKE %:searchValue%"
    )
    Page<VideoProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);

//    @Query(
//            "SELECT new com.dto.StatsDTO(FUNCTION('DATE_FORMAT', v.created_at, '%Y-%m-%d'), COUNT(v)) " +
//                    "FROM VideoEntity v " +
//                    "WHERE v.created_at >= :start AND v.created_at <= :end " +
//                    "GROUP BY FUNCTION('DATE_FORMAT', v.created_at, '%Y-%m-%d')"
//    )
//    List<StatsDTO> findWithSpecCondition(@Param("start") String start, @Param("end") String end);


    @Query("SELECT new com.dto.StatsDTO(FUNCTION('DATE_FORMAT', v.created_at, '%Y-%m-%d'), COUNT(v), " +
            "SUM(CASE WHEN c.id IS NOT NULL THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN l.id IS NOT NULL THEN 1 ELSE 0 END), " +
            "SUM(v.views)) " +
            "FROM VideoEntity v " +
            "LEFT JOIN v.comments c " +
            "LEFT JOIN v.likers l " +
            "WHERE v.created_at >= :start AND v.created_at <= :end " +
            "GROUP BY FUNCTION('DATE_FORMAT', v.created_at, '%Y-%m-%d')")
    List<StatsDTO> findWithSpecCondition(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query(
            "SELECT v.id AS id, v.name AS name, v.video AS video, v.views AS views, v.share_count AS share_count, v.caption AS caption, v.who AS who, v.thumbnail AS thumbnail, v.allow_comment AS allow_comment, v.likes AS likes " +
                    "FROM VideoEntity v " +
                    "WHERE v.id = :id"
    )
    Optional<VideoProjection> findByIdCommon(@Param("id") String id);


    @Query(
            "SELECT v.id AS id, v.name AS name, v.video AS video, v.views AS views, v.share_count AS share_count, v.caption AS caption, v.who AS who, v.thumbnail AS thumbnail, v.allow_comment AS allow_comment, " +
                    "v.user AS user, v.likers AS likers, v.comments AS comments, v.notifications AS notifications, v.hashtags AS hashtags " +
                    "FROM VideoEntity v " +
                    "WHERE v.id = :id"
    )
    Optional<VideoProjection.VideoProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE VideoEntity v SET v.name = :name,v.video = :video,v.views = :views,v.share_count = :share_count,v.caption = :caption,v.who = :who,v.thumbnail = :thumbnail,v.allow_comment = :allow_comment WHERE v.id = :id")
    void updateById(@Param("id") String id, @Param("name") String name, @Param("video") String video, @Param("views") Integer views, @Param("share_count") Integer share_count, @Param("caption") String caption, @Param("thumbnail") String thumbnail, @Param("allow_comment") Boolean allow_comment);

}