package com.example.usermanagement.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.model.RolePermission;

@Repository
public interface RolePermissionRepository
        extends JpaRepository<RolePermission, UUID> {

    List<RolePermission> findByDeletedAtIsNull();

    List<RolePermission> findByRole_IdAndDeletedAtIsNull(UUID roleId);

    List<RolePermission> findByPermission_IdAndDeletedAtIsNull(UUID permissionId);

    Optional<RolePermission> findByRole_IdAndPermission_IdAndDeletedAtIsNull(
            UUID roleId, UUID permissionId
    );
}
