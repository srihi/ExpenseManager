package max93n.services.impl;


import max93n.models.user.Role;
import max93n.models.user.RoleEnum;
import max93n.repositories.RoleRepository;
import max93n.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByRoleName(RoleEnum role) {
        return roleRepository.findByRoleName(role);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    @Override
    public void add(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
