package com.services;

import com.dao.AdminDAO;
import com.dto.AdminDTO;
import com.dto.StatsDTO;
import com.entity.AdminEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interfaces.*;
import com.projection.AdminProjection;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminService implements AdminServiceInterface {
    private final AdminDAO adminDAO;
    private final CustomerServiceInterface customerService;
    private final VideoServiceInterface videoService;
    private final ConversationServiceInterface conversationService;
    private final HashtagServiceInterface hashtagService;
    private final MessageServiceInterface messageService;
    private final NotificationServiceInterface notificationService;
    private final CommentServiceInterface commentService;
    final private PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public AdminService(
            AdminDAO adminDAO,
            VideoServiceInterface videoService,
            CustomerServiceInterface customerService,
            ConversationServiceInterface conversationService,
            HashtagServiceInterface hashtagService,
            MessageServiceInterface messageService,
            NotificationServiceInterface notificationService,
            CommentServiceInterface commentService,
            PasswordEncoder passwordEncoder,
            ObjectMapper objectMapper
    ) {
        this.adminDAO = adminDAO;
        this.customerService = customerService;
        this.videoService = videoService;
        this.conversationService = conversationService;
        this.hashtagService = hashtagService;
        this.messageService = messageService;
        this.notificationService = notificationService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    public Object getAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return principal;
    }

    @Override
    public Page<AdminProjection> getAllAdmins(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return adminDAO.findAllCommon(pageable);
    }

    @Override
    public Page<AdminProjection> getAllAdminsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return adminDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<AdminEntity> getAdminByUsername(String username) {
        return adminDAO.findByUsername(username);
    }

    @Override
    public Optional<AdminProjection> getAdminById(String id) {
        return adminDAO.findByIdCommon(id);
    }


    @Override
    public ResponseEntity<?> createAdmin(Map<String, ?> payload) {
        try {
            AdminEntity adminEntity = new AdminEntity();
            adminEntity.setUsername((String) payload.get("username"));
            adminEntity.setName((String) payload.get("name"));
            adminEntity.setLogo((String) payload.get("logo"));
            adminEntity.setPassword(passwordEncoder.encode((String) payload.get("password")));
            adminEntity.setPermission((String) payload.get("permission"));
            AdminEntity savedEntity = adminDAO.save(adminEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> createData(String table, @RequestBody Map<String, ?> payload) {
        switch (table) {
            case "admin":
                return createAdmin(payload);
            case "hashtag":
                return hashtagService.createHashtag(payload);
            case "customer":
                return customerService.createCustomer(payload);
            default:
                throw new IllegalArgumentException("Unsupported table: " + table);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deteleAdminById(String id) {
        try {
            adminDAO.deleteById(id);
            return ResponseEntity.ok().body("Xóa thành công.");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để xóa.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa dữ liệu.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateAdminById(String id, Map<String, Object> payload) {
        try {
            String username = (String) payload.get("username");
            String name = (String) payload.get("name");
            String permission = (String) payload.get("permission");
            String logo = (String) payload.get("logo");

            adminDAO.updateById(id, username, name, permission, logo);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }

    @Override
    public Page<?> getAllDatas(String table, Integer itemsPerPage, Integer page, String searchValue) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof AdminDTO adminDTO) {
            String permission = adminDTO.getPermission();

            if (!"all".equals(permission) && !"read".equals(permission) && !"write".equals(permission)) {
                throw new IllegalArgumentException("Unauthorized access for table: " + table);
            }
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass().getName());
        }

        if (searchValue != null && !searchValue.isEmpty()) {
            return getPageWithSearchValue(table, itemsPerPage, page, searchValue);
        }

        return getPageWithoutSearchValue(table, itemsPerPage, page);
    }

    private Page<?> getPageWithSearchValue(String table, Integer itemsPerPage, Integer page, String searchValue) {
        switch (table) {
            case "admin":
                return getAllAdminsWithSearchValue(itemsPerPage, page, searchValue);
            case "comment":
                return commentService.getAllCommentsWithSearchValue(itemsPerPage, page, searchValue);
            case "conversation":
                return conversationService.getAllConversationsWithSearchValue(itemsPerPage, page, searchValue);
            case "hashtag":
                return hashtagService.getAllHashtagsWithSearchValue(itemsPerPage, page, searchValue);
            case "notification":
                return notificationService.getAllNotificationsWithSearchValue(itemsPerPage, page, searchValue);
            case "message":
                return messageService.getAllMessagesWithSearchValue(itemsPerPage, page, searchValue);
            case "video":
                return videoService.getAllVideosWithSearchValue(itemsPerPage, page, searchValue);
            case "customer":
                return customerService.getAllCustomersWithSearchValue(itemsPerPage, page, searchValue);
            default:
                throw new IllegalArgumentException("Unsupported table: " + table);
        }
    }

    private Page<?> getPageWithoutSearchValue(String table, Integer itemsPerPage, Integer page) {
        switch (table) {
            case "admin":
                return getAllAdmins(itemsPerPage, page);
            case "comment":
                return commentService.getAllComments(itemsPerPage, page);
            case "conversation":
                return conversationService.getAllConversations(itemsPerPage, page);
            case "hashtag":
                return hashtagService.getAllHashtags(itemsPerPage, page);
            case "notification":
                return notificationService.getAllNotifications(itemsPerPage, page);
            case "message":
                return messageService.getAllMessages(itemsPerPage, page);
            case "video":
                return videoService.getAllVideos(itemsPerPage, page);
            case "customer":
                return customerService.getAllCustomers(itemsPerPage, page);
            default:
                throw new IllegalArgumentException("Unsupported table: " + table);
        }
    }

    public List<StatsDTO> getStats(LocalDate start, LocalDate end, String filters) {
        return videoService.getVideosForStats(start, end, filters);
    }

    @Override
    public Optional<?> getDataFromTableAndId(String table, String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof AdminDTO adminDTO) {
            String permission = adminDTO.getPermission();

            if (!"all".equals(permission) && !"read".equals(permission) && !"write".equals(permission)) {
                throw new IllegalArgumentException("Unauthorized access for table: " + table);
            }
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass().getName());
        }
        switch (table) {
            case "comment":
                return commentService.getCommentById(id);
            case "conversation":
                return conversationService.getConversationById(id);
            case "hashtag":
                return hashtagService.getHashtagById(id, false);
            case "notification":
                return notificationService.getNotificationById(id);
            case "message":
                return messageService.getMessageById(id);
            case "video":
                return videoService.getVideoById(id);
            case "customer":
                return customerService.getCustomerById(id);
            case "admin":
                return this.getAdminById(id);
//            case "following":
//                return this.getAdminById(id);
//            case "video_hashtag":
//                return this.getAdminById(id);
//            case "video_like":
//                return this.getAdminById(id);
            default:
                throw new IllegalArgumentException("Unsupported table: " + table);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteDataFromTableAndId(String table, String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof AdminDTO adminDTO) {
            String permission = adminDTO.getPermission();

            if (!"all".equals(permission) && !"write".equals(permission)) {
                throw new IllegalArgumentException("Unauthorized access for table: " + table);
            }
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass().getName());
        }
        try {
            switch (table) {
                case "comment":
                    commentService.deteleCommentById(id);
                    break;
                case "conversation":
                    conversationService.deteleConversationById(id);
                    break;
                case "hashtag":
                    hashtagService.deteleHashtagById(id);
                    break;
                case "notification":
                    notificationService.deteleNotificationById(id);
                    break;
                case "message":
                    messageService.deteleMessageById(id);
                    break;
                case "video":
                    videoService.deteleVideoById(id);
                    break;
                case "customer":
                    customerService.deteleCustomerById(id);
                    break;
                case "admin":
                    this.deteleAdminById(id);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported table: " + table);
            }
            return ResponseEntity.ok("Deleted successfully.");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để xóa.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa dữ liệu.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateDataFromTableAndId(String table, String id, Map<String, Object> payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof AdminDTO adminDTO) {
            String permission = adminDTO.getPermission();

            if (!"all".equals(permission) && !"write".equals(permission)) {
                throw new IllegalArgumentException("Unauthorized access for table: " + table);
            }
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass().getName());
        }
        try {
            switch (table) {
                case "comment":
                    return commentService.updateCommentById(id, payload);
                case "conversation":
                    return conversationService.updateConversationById(id, payload);
                case "hashtag":
                    return hashtagService.updateHashtagById(id, payload);
                case "notification":
                    return notificationService.updateNotificationById(id, payload);
                case "message":
                    return messageService.updateMessageById(id, payload);
                case "video":
                    return videoService.updateVideoById(id, payload);
                case "customer":
                    return customerService.updateCustomerById(id, payload);
                case "admin":
                    return this.updateAdminById(id, payload);
                default:
                    throw new IllegalArgumentException("Unsupported table: " + table);
            }
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }


}

