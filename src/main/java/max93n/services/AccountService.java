package max93n.services;

import max93n.models.account.Account;
import max93n.models.user.User;

import java.util.List;

public interface AccountService {
    List<Account> getAllByUser(User user);
    void add(Account account);
    void save(Account account);
    void remove(Account account);
    Account getByName(String name);
}
