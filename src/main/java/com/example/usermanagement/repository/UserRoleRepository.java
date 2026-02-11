package com.example.usermanagement.repository;
import com.example.usermanagement.model.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    List<UserRole> findByDeletedAtIsNull();

    List<UserRole> findByUser_IdAndDeletedAtIsNull(UUID userId);

    List<UserRole> findByRole_IdAndDeletedAtIsNull(UUID roleId);

    Optional<UserRole> findByUser_IdAndRole_IdAndDeletedAtIsNull(
            UUID userId, UUID roleId
    );
}
