package com.controller;

import com.dto.StatsDTO;
import com.services.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{table}")
    public Page<?> getAllDataFromTable(
            @PathVariable String table,
            @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "") String searchValue
    ) {
        return adminService.getAllDatas(table, itemsPerPage, page, searchValue);
    }

    @GetMapping("/{table}/{id}")
    public Optional<?> getDataFromTableAndId(
            @PathVariable String table,
            @PathVariable String id
    ) {
        return adminService.getDataFromTableAndId(table, id);
    }

    @PostMapping("/create/{table}")
    public ResponseEntity<?> createData(
            @PathVariable String table,
            @RequestBody Map<String, ?> payload
    ) {
        ResponseEntity<?> createdAdmin = adminService.createData(table, payload);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @GetMapping("/verify")
    public Object verifyAdmin() {
        return adminService.getAdmin();
    }

    @PostMapping("/update/{table}/{id}")
    public ResponseEntity<String> updateDataFromTableAndId(
            @PathVariable String table,
            @PathVariable String id,
            @RequestBody Map<String, Object> payload
    ) {
        String action = (String) payload.get("action");

        if ("update".equals(action)) {
            return adminService.updateDataFromTableAndId(table, id, payload);
        } else if ("delete".equals(action)) {
            return adminService.deleteDataFromTableAndId(table, id);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    @GetMapping("/stats/video")
    public ResponseEntity<List<StatsDTO>> getStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam String filters) {

        List<StatsDTO> stats = adminService.getStats(start, end, filters);
        return ResponseEntity.ok(stats);
    }
}
