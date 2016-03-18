package max93n.services;

import max93n.models.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAll();
    void add(Account account);
    void remove(Account account);
    Account getById(Long id);
    Account getByName(String name);
}
