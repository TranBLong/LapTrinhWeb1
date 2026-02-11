package com.example.usermanagement.dto;

import java.util.UUID;

public class PermissionResponse {
    public UUID id;
    public String permissionCode;
    public String permissionName;
    public String module;
    public String description;
    public Boolean isActive;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getPermissionCode() {
        return permissionCode;
    }
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    public String getPermissionName() {
        return permissionName;
    }
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
