package com.bv.platform.iam.domain.repositories;

import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.domain.model.valueobjects.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findAll();
    Optional<Role> findByName(Roles name);
    boolean existsByName(Roles name);
    Role save(Role role);
}
