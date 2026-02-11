package com.example.usermanagement.service;
import java.util.List;
import java.util.UUID;
import com.example.usermanagement.dto.RolePermissionCreateRequest;
import com.example.usermanagement.dto.RolePermissionResponse;

public interface RolePermissionService {

    List<RolePermissionResponse> getAll();

    RolePermissionResponse assign(RolePermissionCreateRequest request);

    RolePermissionResponse update(UUID id, RolePermissionCreateRequest request);

    RolePermissionResponse updateStatus(UUID id, Boolean isActive);

    void remove(UUID id);

    List<RolePermissionResponse> getByRole(UUID roleId);

    List<RolePermissionResponse> getByPermission(UUID permissionId);
}
