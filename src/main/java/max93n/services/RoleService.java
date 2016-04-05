package max93n.services;

import max93n.entities.Role;
import max93n.enums.RoleEnum;

import java.util.List;

public interface RoleService {
    Role findByRoleName(RoleEnum role);

    Role findById(Long id);
    void add(Role role);

    List<Role> getRoles();

}
