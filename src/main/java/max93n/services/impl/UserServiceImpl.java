package max93n.services.impl;

import max93n.entities.Role;
import max93n.entities.User;
import max93n.entities.RoleEnum;
import max93n.repositories.UserRepository;
import max93n.services.RoleService;
import max93n.services.UserService;
import max93n.utils.PasswordHelper;
import max93n.utils.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordHelper passwordHelper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    public void remove(User user) {
        Iterator<Role> iterator = user.getUserRoles().iterator();
        while(iterator.hasNext()) {
            user.getUserRoles().remove(iterator.next());
        }
        userRepository.saveAndFlush(user);
        userRepository.delete(user.getId());
    }

    @Transactional
    public void add(User newUser, Set<RoleEnum> roleEnums) throws UserExistsException {

        User user;
        user = userRepository.findByUsername(newUser.getUsername());
        if (user != null) {
            throw new UserExistsException("There is an account with that username: " + newUser.getUsername());
        }

        Set<Role> roles = new HashSet<>();
        for (RoleEnum role : roleEnums) {
            Role userRole = roleService.findByRoleName(role);
            roles.add(userRole);
        }
        newUser.setUserRoles(roles);

        userRepository.saveAndFlush(newUser);
    }

    public User registerNewUserAccount(String username, String password, String email) throws UserExistsException {


        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordHelper.encodePassword(password, null));
        newUser.setRegistrationDate(new Date());
        newUser.setEmail(email);

        Set<RoleEnum> roleEnums = new HashSet<>();
        roleEnums.add(RoleEnum.USER);

        

        add(newUser, roleEnums);

        return newUser;
    }
}
