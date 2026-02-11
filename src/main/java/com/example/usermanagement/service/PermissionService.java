package com.example.usermanagement.service;

import java.util.List;
import java.util.UUID;

import com.example.usermanagement.dto.PermissionCreateRequest;
import com.example.usermanagement.dto.PermissionResponse;

public interface PermissionService {

    List<PermissionResponse> getAll();
    List<PermissionResponse> search(String keyword);
    PermissionResponse getById(UUID id);
    PermissionResponse create(PermissionCreateRequest request);
    PermissionResponse update(UUID id, PermissionCreateRequest request);
    void delete(UUID id);

    List<PermissionResponse> getByModule(String module);
}
