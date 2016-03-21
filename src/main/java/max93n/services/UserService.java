package max93n.services;

import max93n.entities.User;
import max93n.entities.RoleEnum;
import max93n.utils.exceptions.UserExistsException;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getUsers();
    User findByUsername(String username);
    User findById(Long id);
    void add(User newUser, Set<RoleEnum> roleEnums) throws UserExistsException;
    void save(User user);
    void remove(User user);
    User registerNewUserAccount(String username, String password, String email) throws UserExistsException;
}
