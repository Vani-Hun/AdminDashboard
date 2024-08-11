package com.interfaces;

import com.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface CustomerServiceInterface {
    ResponseEntity<String> updateCustomerById(String id, Map<String, Object> payload);

    Page<CustomerProjection> getAllCustomers(Integer itemsPerPage, Integer page);

    Page<CustomerProjection> getAllCustomersWithSearchValue(Integer itemsPerPage, Integer page, String searchValue);

    Optional<CustomerProjection> getCustomerById(String id);

    ResponseEntity<?> createCustomer(Map<String, ?> payload);

    Optional<?> deteleCustomerById(String id);

}
