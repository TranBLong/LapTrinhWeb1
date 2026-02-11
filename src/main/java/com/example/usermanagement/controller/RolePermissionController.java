package com.example.usermanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.RolePermissionCreateRequest;
import com.example.usermanagement.dto.RolePermissionResponse;
import com.example.usermanagement.service.RolePermissionService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/role-permissions")
@CrossOrigin
public class RolePermissionController {

    @Autowired
    private RolePermissionService service;

    @GetMapping
    public List<RolePermissionResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    public RolePermissionResponse assign(
            @RequestBody RolePermissionCreateRequest request
    ) {
        return service.assign(request);
    }

    @PutMapping("/{id}")
    public RolePermissionResponse update(
            @PathVariable UUID id,
            @RequestBody RolePermissionCreateRequest request
    ) {
        return service.update(id, request);
    }

    @PutMapping("/{id}/status/{active}")
    public RolePermissionResponse updateStatus(
            @PathVariable UUID id,
            @PathVariable Boolean active
    ) {
        return service.updateStatus(id, active);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable UUID id) {
        service.remove(id);
    }

    @GetMapping("/role/{roleId}")
    public List<RolePermissionResponse> getByRole(
            @PathVariable UUID roleId
    ) {
        return service.getByRole(roleId);
    }

    @GetMapping("/permission/{permissionId}")
    public List<RolePermissionResponse> getByPermission(
            @PathVariable UUID permissionId
    ) {
        return service.getByPermission(permissionId);
    }
}
