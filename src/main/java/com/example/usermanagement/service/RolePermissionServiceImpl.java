package com.example.usermanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.usermanagement.dto.RolePermissionCreateRequest;
import com.example.usermanagement.dto.RolePermissionResponse;
import com.example.usermanagement.model.Permission;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.RolePermission;
import com.example.usermanagement.repository.PermissionRepository;
import com.example.usermanagement.repository.RolePermissionRepository;
import com.example.usermanagement.repository.RoleRepository;

@Service
public class RolePermissionServiceImpl
        implements RolePermissionService {

    @Autowired
    private RolePermissionRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<RolePermissionResponse> getAll() {
        return repository.findByDeletedAtIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public RolePermissionResponse assign(
            RolePermissionCreateRequest req
    ) {
        Optional<RolePermission> existed =
                repository.findByRole_IdAndPermission_IdAndDeletedAtIsNull(
                        req.roleId, req.permissionId
                );

        if (existed.isPresent()) {
            throw new RuntimeException("Permission đã được gán cho role");
        }

        RolePermission rp = new RolePermission();
        Role role = roleRepository.findById(req.roleId)
                .orElseThrow(() -> new RuntimeException("Role khÃ´ng tá»“n táº¡i"));
        Permission permission = permissionRepository.findById(req.permissionId)
                .orElseThrow(() -> new RuntimeException("Permission khÃ´ng tá»“n táº¡i"));
        rp.setRole(role);
        rp.setPermission(permission);
        rp.setCreatedAt(LocalDateTime.now());
        rp.setIsActive(true);

        return toResponse(repository.save(rp));
    }

    @Override
    public RolePermissionResponse update(
            UUID id,
            RolePermissionCreateRequest req
    ) {
        RolePermission rp = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mapping khong ton tai")
                );

        if (rp.getDeletedAt() != null) {
            throw new RuntimeException("Mapping da bi xoa");
        }

        Optional<RolePermission> existed =
                repository.findByRole_IdAndPermission_IdAndDeletedAtIsNull(
                        req.roleId, req.permissionId
                );
        if (existed.isPresent() && !existed.get().getId().equals(id)) {
            throw new RuntimeException("Permission da duoc gan cho role");
        }

        Role role = roleRepository.findById(req.roleId)
                .orElseThrow(() -> new RuntimeException("Role khong ton tai"));
        Permission permission = permissionRepository.findById(req.permissionId)
                .orElseThrow(() -> new RuntimeException("Permission khong ton tai"));

        rp.setRole(role);
        rp.setPermission(permission);

        return toResponse(repository.save(rp));
    }

    @Override
    public RolePermissionResponse updateStatus(UUID id, Boolean isActive) {
        RolePermission rp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping khong ton tai"));

        if (rp.getDeletedAt() != null) {
            throw new RuntimeException("Mapping da bi xoa");
        }

        rp.setIsActive(isActive);
        rp.setUpdatedAt(LocalDateTime.now());

        return toResponse(repository.save(rp));
    }

    public RolePermissionRepository getRepository() {
        return repository;
    }

    public void setRepository(RolePermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(UUID id) {
        RolePermission rp = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mapping không tồn tại")
                );

        rp.setDeletedAt(LocalDateTime.now());
        rp.setIsActive(false);

        repository.save(rp);
    }

    @Override
    public List<RolePermissionResponse> getByRole(UUID roleId) {
        return repository.findByRole_IdAndDeletedAtIsNull(roleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<RolePermissionResponse> getByPermission(
            UUID permissionId
    ) {
        return repository
                .findByPermission_IdAndDeletedAtIsNull(permissionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private RolePermissionResponse toResponse(RolePermission rp) {
        RolePermissionResponse res = new RolePermissionResponse();
        res.id = rp.getId();
        res.roleId = rp.getRole() != null ? rp.getRole().getId() : null;
        res.permissionId = rp.getPermission() != null ? rp.getPermission().getId() : null;
        res.isActive = rp.getIsActive();
        return res;
    }
}
