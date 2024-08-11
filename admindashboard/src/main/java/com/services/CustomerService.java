package com.services;

import com.dao.CustomerDAO;
import com.entity.CustomerEntity;
import com.interfaces.CustomerServiceInterface;
import com.mapper.CustomerMapper;
import com.projection.CustomerProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceInterface {
    final private CustomerDAO customerDAO;
    final private CustomerMapper customerMapper;
    final private PasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerDAO customerDAO,
            CustomerMapper customerMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.customerDAO = customerDAO;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;

    }

    public Optional<CustomerEntity> getCustomerByUsername(String username) {
        return customerDAO.findByUsername(username);
    }

    @Override
    public Page<CustomerProjection> getAllCustomers(Integer itemsPerPage, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return customerDAO.findAllCommon(pageable);
    }

    @Override
    public Page<CustomerProjection> getAllCustomersWithSearchValue(Integer itemsPerPage, Integer page, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
        return customerDAO.findAllCommonWithSearchValue(pageable, searchValue);
    }

    @Override
    public Optional<CustomerProjection> getCustomerById(String id) {
        return customerDAO.findByIdCommon(id);
    }

    @Override
    public ResponseEntity<?> createCustomer(Map<String, ?> payload) {
        try {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setName((String) payload.get("name"));
            customerEntity.setUsername((String) payload.get("username"));
            customerEntity.setEmail((String) payload.get("email"));
            customerEntity.setLogo((String) payload.get("logo"));
            customerEntity.setPhone((String) payload.get("phone"));
            customerEntity.setPassword(passwordEncoder.encode((String) payload.get("password")));
            CustomerEntity savedEntity = customerDAO.save(customerEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public Optional<?> deteleCustomerById(String id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateCustomerById(String id, Map<String, Object> payload) {
        try {
            String logo = (String) payload.get("logo");
            String name = (String) payload.get("name");
            String username = (String) payload.get("username");
            String email = (String) payload.get("email");
            Integer phone = (Integer) payload.get("phone");
            String bio = (String) payload.get("bio");
            String message_status = (String) payload.get("message_status");
            Boolean video_liked_status = (Boolean) payload.get("video_liked_status");
            String permission = (String) payload.get("permission");
            Integer likes = (Integer) payload.get("likes");

            customerDAO.updateById(id, logo, name, username, email, phone, bio, message_status, video_liked_status, permission, likes);
            return ResponseEntity.ok("Cập nhật thành công.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id để cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật dữ liệu.");
        }
    }
}

