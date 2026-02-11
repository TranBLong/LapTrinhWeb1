package com.example.usermanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.example.usermanagement.dto.PermissionCreateRequest;
import com.example.usermanagement.dto.PermissionResponse;
import com.example.usermanagement.model.Permission;
import com.example.usermanagement.repository.PermissionRepository;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionResponse> getAll() {
        return permissionRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PermissionResponse> search(String keyword) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        if (normalizedKeyword.isEmpty()) {
            return getAll();
        }

        return permissionRepository.searchByKeyword(normalizedKeyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PermissionResponse getById(UUID id) {
        Permission permission = getPermission(id);
        return toResponse(permission);
    }

    @Override
    public PermissionResponse create(PermissionCreateRequest req) {
        Permission p = new Permission();
        p.setPermissionCode(req.permissionCode);
        p.setPermissionName(req.permissionName);
        p.setModule(req.module);
        p.setDescription(req.description);
        p.setIsActive(true);
        p.setCreatedAt(LocalDateTime.now());

        return toResponse(permissionRepository.save(p));
    }

    @Override
    public PermissionResponse update(UUID id, PermissionCreateRequest req) {
        Permission p = getPermission(id);

        p.setPermissionName(req.permissionName);
        p.setModule(req.module);
        p.setDescription(req.description);
        p.setUpdatedAt(LocalDateTime.now());

        return toResponse(permissionRepository.save(p));
    }

    @Override
    public void delete(UUID id) {
        Permission p = getPermission(id);

        p.setDeletedAt(LocalDateTime.now());
        p.setIsActive(false);

        permissionRepository.save(p);
    }

    @Override
    public List<PermissionResponse> getByModule(String module) {
        return permissionRepository
                .findByModuleAndDeletedAtIsNull(module)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ===== helper =====
    private Permission getPermission(UUID id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    private PermissionResponse toResponse(Permission p) {
        PermissionResponse res = new PermissionResponse();
        res.id = p.getId();
        res.permissionCode = p.getPermissionCode();
        res.permissionName = p.getPermissionName();
        res.module = p.getModule();
        res.description = p.getDescription();
        res.isActive = p.getIsActive();
        return res;
    }
}
