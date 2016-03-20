package max93n.services;

import max93n.models.user.Role;
import max93n.models.user.RoleEnum;

import java.util.List;

public interface RoleService {
    Role findByRoleName(RoleEnum role);

    Role findById(Long id);
    void add(Role role);

    List<Role> getRoles();

}
