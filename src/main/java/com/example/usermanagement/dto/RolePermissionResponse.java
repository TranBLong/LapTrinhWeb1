package com.example.usermanagement.dto;

import java.util.UUID;

public class RolePermissionResponse {
    public UUID id;
    public UUID roleId;
    public UUID permissionId;
    public Boolean isActive;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
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
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
}
    