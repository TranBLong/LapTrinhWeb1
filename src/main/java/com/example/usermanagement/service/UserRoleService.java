package com.example.usermanagement.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.UserRoleRequest;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import com.example.usermanagement.model.UserRole;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.repository.UserRoleRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(
            UserRoleRepository repository,
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserRole> getAll() {
        return repository.findByDeletedAtIsNull();
    }

    public List<UserRole> getByUser(UUID userId) {
        return repository.findByUser_IdAndDeletedAtIsNull(userId);
    }

    public List<UserRole> getByRole(UUID roleId) {
        return repository.findByRole_IdAndDeletedAtIsNull(roleId);
    }

    public UserRole assignRole(UserRoleRequest request) {

        if (repository.findByUser_IdAndRole_IdAndDeletedAtIsNull(
                request.getUserId(), request.getRoleId()).isPresent()) {
            throw new RuntimeException("User da co role nay");
        }

        UserRole ur = new UserRole();
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User khong ton tai"));
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role khong ton tai"));
        ur.setUser(user);
        ur.setRole(role);
        ur.setIsActive(true);

        return repository.save(ur);
    }

    public UserRole update(UUID id, UserRoleRequest request) {
        UserRole ur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay mapping"));

        if (ur.getDeletedAt() != null) {
            throw new RuntimeException("Mapping da bi xoa");
        }

        Optional<UserRole> existed = repository.findByUser_IdAndRole_IdAndDeletedAtIsNull(
                request.getUserId(), request.getRoleId()
        );
        if (existed.isPresent() && !existed.get().getId().equals(id)) {
            throw new RuntimeException("User da co role nay");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User khong ton tai"));
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role khong ton tai"));

        ur.setUser(user);
        ur.setRole(role);
        ur.setUpdatedAt(LocalDateTime.now());

        return repository.save(ur);
    }

    public UserRole updateStatus(UUID id, Boolean active) {
        UserRole ur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay mapping"));

        if (ur.getDeletedAt() != null) {
            throw new RuntimeException("Mapping da bi xoa");
        }

        ur.setIsActive(active);
        ur.setUpdatedAt(LocalDateTime.now());

        return repository.save(ur);
    }

    public void delete(UUID id) {
        UserRole ur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay mapping"));

        ur.setIsActive(false);
        ur.setDeletedAt(LocalDateTime.now());

        repository.save(ur);
    }
}
