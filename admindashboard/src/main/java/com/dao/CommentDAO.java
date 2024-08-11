package com.dao;

import com.entity.CommentEntity;
import com.projection.CommentProjection;
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
public interface CommentDAO extends JpaRepository<CommentEntity, String> {
    Page<CommentEntity> findAll(Pageable pageable);

    Optional<CommentEntity> findById(String id);

    @Query(
            "SELECT c.id AS id, c.text AS text " +
                    "FROM CommentEntity c "
    )
    Page<CommentProjection> findAllCommon(Pageable pageable);

    @Query(
            "SELECT c.id AS id, c.text AS text " +
                    "FROM CommentEntity c " +
                    "WHERE c.text LIKE %:searchValue%"
    )
    Page<CommentProjection> findAllCommonWithSearchValue(Pageable pageable, @Param("searchValue") String searchValue);


    @Query(
            "SELECT c.id AS id, c.text AS text " +
                    "FROM CommentEntity c " +
                    "WHERE c.id = :id"
    )
    Optional<CommentProjection> findByIdCommon(@Param("id") String id);

    @Query(
            "SELECT c.id AS id, c.text AS text, c.video AS video, c.customer AS customer " +
                    "FROM CommentEntity c " +
                    "WHERE c.id = :id"
    )
    Optional<CommentProjection.CommentProjectionWithAll> findByIdWithAll(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentEntity c WHERE c.id = :id")
    void deleteById(@Param("id") String id);

    //    @Modifying
//    @Transactional
//    @Query("UPDATE CommentEntity c SET c.text = :text, c.video = :video, c.customer = :customer WHERE c.id = :id")
//    void updateById(@Param("id") String id, @Param("text") String text, @Param("video") VideoEntity video, @Param("customer") CustomerEntity customer);
    @Modifying
    @Transactional
    @Query("UPDATE CommentEntity c SET c.text = :text WHERE c.id = :id")
    void updateById(@Param("id") String id, @Param("text") String text);

}
