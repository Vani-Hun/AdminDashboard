package com.interfaces;

import com.dto.StatsDTO;
import com.projection.VideoProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VideoServiceInterface {
    ResponseEntity<String> updateVideoById(String id, Map<String, Object> payload);

    Page<VideoProjection> getAllVideos(Integer itemsPerPage, Integer page);

    Page<VideoProjection> getAllVideosWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);

    Optional<VideoProjection> getVideoById(String id);

    Optional<?> deteleVideoById(String id);

    List<StatsDTO> getVideosForStats(LocalDate start, LocalDate end, String filters);
}
