package com.services;

import com.dao.VideoDAO;
import com.dto.StatsDTO;
import com.interfaces.VideoServiceInterface;
import com.projection.VideoProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VideoService implements VideoServiceInterface {
    final private VideoDAO videoDAO;

    public VideoService(VideoDAO videoDAO) {
        this.videoDAO = videoDAO;
    }

    @Override
    public Page<VideoProjection> getAllVideos(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return videoDAO.findAllCommon(pageable);
    }

    @Override
    public Page<VideoProjection> getAllVideosWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return videoDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<VideoProjection> getVideoById(String id) {
        return videoDAO.findByIdCommon(id);
    }

    @Override
    public Optional<?> deteleVideoById(String id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateVideoById(String id, Map<String, Object> payload) {
        try {
            String name = (String) payload.get("name");
            String video = (String) payload.get("video");
            Integer share_count = (Integer) payload.get("share_count");
            String thumbnail = (String) payload.get("thumbnail");
            Integer views = (Integer) payload.get("views");
            String caption = (String) payload.get("caption");
            Boolean allow_comment = (Boolean) payload.get("allow_comment");

            videoDAO.updateById(id, name, video, views, share_count, caption, thumbnail, allow_comment);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }

    @Override
    public List<StatsDTO> getVideosForStats(LocalDate start, LocalDate end, String filters) {

        LocalDateTime startDate = start.atStartOfDay(); // Bắt đầu từ đầu ngày
        LocalDateTime endDate = end.atTime(LocalTime.MAX); // Kết thúc vào cuối ngày
//        LocalDateTime startDate = start.atTime(0, 0, 0); // Bắt đầu từ đầu ngày (00:00:00)
//        LocalDateTime endDate = end.atTime(23, 59, 59); // Kết thúc vào cuối ngày (23:59:59)
        return videoDAO.findWithSpecCondition(startDate, endDate);
    }
}
