package com.bv.platform.iam.application.queryservices;

import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.bv.platform.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
