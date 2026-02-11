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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import com.example.usermanagement.dto.PermissionCreateRequest;
import com.example.usermanagement.dto.PermissionResponse;
import com.example.usermanagement.service.PermissionService;

@RestController
@RequestMapping("/api/permissions")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<PermissionResponse> getAll() {
        return permissionService.getAll();
    }

    @GetMapping("/search")
    public List<PermissionResponse> search(
            @RequestParam String keyword
    ) {
        return permissionService.search(keyword);
    }

    @GetMapping("/{id}")
    public PermissionResponse getById(@PathVariable UUID id) {
        return permissionService.getById(id);
    }

    @PostMapping
    public PermissionResponse create(
            @RequestBody PermissionCreateRequest request
    ) {
        return permissionService.create(request);
    }

    @PutMapping("/{id}")
    public PermissionResponse update(
            @PathVariable UUID id,
            @RequestBody PermissionCreateRequest request
    ) {
        return permissionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        permissionService.delete(id);
    }

    @GetMapping("/module/{module}")
    public List<PermissionResponse> getByModule(
            @PathVariable String module
    ) {
        return permissionService.getByModule(module);
    }
}
