package com.example.usermanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    List<Permission> findByDeletedAtIsNull();

    List<Permission> findByModuleAndDeletedAtIsNull(String module);

    @Query("""
            SELECT p
            FROM Permission p
            WHERE p.deletedAt IS NULL
                AND (
                    LOWER(p.permissionCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(COALESCE(p.permissionName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(COALESCE(p.module, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(COALESCE(p.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
            """)
    List<Permission> searchByKeyword(@Param("keyword") String keyword);
}
