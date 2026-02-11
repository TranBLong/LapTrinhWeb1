package com.example.usermanagement.service;

import java.util.List;
import java.util.UUID;

import com.example.usermanagement.dto.RoleCreateRequest;
import com.example.usermanagement.dto.RoleResponse;

public interface RoleService {

    List<RoleResponse> getAll();
    List<RoleResponse> search(String keyword);
    RoleResponse getById(UUID id);
    RoleResponse create(RoleCreateRequest request);
    RoleResponse update(UUID id, RoleCreateRequest request);
    void delete(UUID id);

    // permissions (mock)
    List<String> getPermissions(UUID roleId);
    void addPermission(UUID roleId, UUID permissionId);
    void removePermission(UUID roleId, UUID permissionId);
}
