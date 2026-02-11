package com.example.usermanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleCode(String roleCode);

    List<Role> findByDeletedAtIsNull();

    @Query("""
            SELECT r
            FROM Role r
            WHERE r.deletedAt IS NULL
                AND (
                    LOWER(r.roleCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(COALESCE(r.roleName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(COALESCE(r.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
            """)
    List<Role> searchByKeyword(@Param("keyword") String keyword);
}
