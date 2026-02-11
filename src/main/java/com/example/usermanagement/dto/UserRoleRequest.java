package com.example.usermanagement.dto;

import java.util.UUID;

public class UserRoleRequest {
    private UUID userId;
    private UUID roleId;

    // getter / setter
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
