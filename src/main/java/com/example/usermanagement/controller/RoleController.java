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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.RoleCreateRequest;
import com.example.usermanagement.dto.RoleResponse;
import com.example.usermanagement.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleResponse> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/search")
    public List<RoleResponse> search(
            @RequestParam String keyword
    ) {
        return roleService.search(keyword);
    }

    @GetMapping("/{id}")
    public RoleResponse getById(@PathVariable UUID id) {
        return roleService.getById(id);
    }

    @PostMapping
    public RoleResponse create(@RequestBody RoleCreateRequest request) {
        return roleService.create(request);
    }

    @PutMapping("/{id}")
    public RoleResponse update(
            @PathVariable UUID id,
            @RequestBody RoleCreateRequest request
    ) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        roleService.delete(id);
    }

    // ===== permissions =====

    @GetMapping("/{id}/permissions")
    public List<String> getPermissions(@PathVariable UUID id) {
        return roleService.getPermissions(id);
    }

    @PostMapping("/{id}/permissions")
    public void addPermission(
            @PathVariable UUID id,
            @RequestParam UUID permissionId
    ) {
        roleService.addPermission(id, permissionId);
    }

    @DeleteMapping("/{id}/permissions/{permissionId}")
    public void removePermission(
            @PathVariable UUID id,
            @PathVariable UUID permissionId
    ) {
        roleService.removePermission(id, permissionId);
    }
}
