package com.example.usermanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.UserRoleRequest;
import com.example.usermanagement.model.UserRole;
import com.example.usermanagement.service.UserRoleService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserRole> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<UserRole> getByUser(@PathVariable UUID userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/role/{roleId}")
    public List<UserRole> getByRole(@PathVariable UUID roleId) {
        return service.getByRole(roleId);
    }

    @PostMapping
    public UserRole assign(@RequestBody UserRoleRequest request) {
        return service.assignRole(request);
    }

    @PutMapping("/{id}")
    public UserRole update(
            @PathVariable UUID id,
            @RequestBody UserRoleRequest request
    ) {
        return service.update(id, request);
    }

    @PutMapping("/{id}/status/{active}")
    public UserRole updateStatus(
            @PathVariable UUID id,
            @PathVariable Boolean active
    ) {
        return service.updateStatus(id, active);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
