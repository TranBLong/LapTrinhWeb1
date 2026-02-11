package com.example.usermanagement.service;
import com.example.usermanagement.dto.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.usermanagement.repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.dto.RoleCreateRequest;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<RoleResponse> search(String keyword) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        if (normalizedKeyword.isEmpty()) {
            return getAll();
        }

        return roleRepository.searchByKeyword(normalizedKeyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public RoleResponse getById(UUID id) {
        Role role = getRole(id);
        return toResponse(role);
    }

    @Override
    public RoleResponse create(RoleCreateRequest req) {
        System.out.println("roleCode = " + req.roleCode);
        Role role = new Role();
        role.setRoleCode(req.roleCode);
        role.setRoleName(req.roleName);
        role.setDescription(req.description);
        role.setIsSystem(Boolean.TRUE.equals(req.isSystem));
        role.setIsActive(true);
        role.setCreatedAt(LocalDateTime.now());

        return toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse update(UUID id, RoleCreateRequest req) {
        Role role = getRole(id);

        role.setRoleName(req.roleName);
        role.setDescription(req.description);
        role.setUpdatedAt(LocalDateTime.now());

        return toResponse(roleRepository.save(role));
    }

    @Override
    public void delete(UUID id) {
        Role role = getRole(id);

        if (Boolean.TRUE.equals(role.getIsSystem())) {
            throw new RuntimeException("Khong the xoa role he thong");
        }

        role.setDeletedAt(LocalDateTime.now());
        role.setIsActive(false);
        roleRepository.save(role);
    }

    // ===== MOCK permissions =====

    @Override
    public List<String> getPermissions(UUID roleId) {
        return List.of("READ", "WRITE", "DELETE"); // mock
    }

    @Override
    public void addPermission(UUID roleId, UUID permissionId) {
        // mock - chua can DB
    }

    @Override
    public void removePermission(UUID roleId, UUID permissionId) {
        // mock - chua can DB
    }

    // ===== helper =====
    private Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    private RoleResponse toResponse(Role role) {
        RoleResponse res = new RoleResponse();
        res.id = role.getId();
        res.roleCode = role.getRoleCode();
        res.roleName = role.getRoleName();
        res.description = role.getDescription();
        res.isSystem = role.getIsSystem();
        res.isActive = role.getIsActive();
        return res;
    }
}
