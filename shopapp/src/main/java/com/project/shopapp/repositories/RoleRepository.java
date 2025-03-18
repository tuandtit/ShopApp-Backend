package com.project.shopapp.repositories;

import com.project.shopapp.common.enums.ERole;
import com.project.shopapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole eRole);
}
