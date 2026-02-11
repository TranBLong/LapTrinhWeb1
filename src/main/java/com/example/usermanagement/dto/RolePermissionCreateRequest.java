package com.example.usermanagement.dto;

import java.util.UUID;

public class RolePermissionCreateRequest {
    public UUID roleId;
    public UUID permissionId;
    public UUID getRoleId() {
        return roleId;
    }
    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
    public UUID getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(UUID permissionId) {
        this.permissionId = permissionId;
    }
}
