package com.bv.platform.iam.domain.model.entities;

import com.bv.platform.iam.domain.model.valueobjects.Roles;
import lombok.*;

import java.util.List;

/**
 * Role domain entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@EqualsAndHashCode
@ToString
public class Role {
    private Long id;
    private Roles name;

    public Role(Roles name) {
        this.name = name;
    }

    public String getStringName() {
        return name.name();
    }

    public static Role getDefaultRole() {
        return new Role(Roles.ROLE_STUDENT);
    }

    public static Role toRoleFromName(String name) {
        try {
            return new Role(Roles.valueOf(name));
        } catch (IllegalArgumentException e) {
            if ("ESTUDIANTE".equalsIgnoreCase(name)) return new Role(Roles.ROLE_STUDENT);
            if ("ONG".equalsIgnoreCase(name) || "REPRESENTANTE_ONG".equalsIgnoreCase(name)) return new Role(Roles.ROLE_ORGANIZATION);
            if ("ADMIN".equalsIgnoreCase(name) || "ADMINISTRADOR".equalsIgnoreCase(name)) return new Role(Roles.ROLE_ADMIN);
            throw e;
        }
    }

    public static List<Role> validateRoleSet(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return roles;
    }
}
