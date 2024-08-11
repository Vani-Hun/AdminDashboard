package com.interfaces;

import com.entity.AdminEntity;
import com.projection.AdminProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface AdminServiceInterface {
    ResponseEntity<String> updateAdminById(String id, Map<String, Object> payload);

    ResponseEntity<?> createAdmin(Map<String, ?> payload);

    Page<AdminProjection> getAllAdmins(Integer itemsPerPage, Integer page);

    Optional<AdminEntity> getAdminByUsername(String username);

    Optional<AdminProjection> getAdminById(String id);

    Page<?> getAllDatas(String table, Integer itemsPerPage, Integer page, String searchValue);

    Page<?> getAllAdminsWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);

    Optional<?> getDataFromTableAndId(String table, String id);

    ResponseEntity<String> updateDataFromTableAndId(String table, String id, Map<String, Object> payload);

    ResponseEntity<String> deleteDataFromTableAndId(String table, String id);

    ResponseEntity<String> deteleAdminById(String id);
}
