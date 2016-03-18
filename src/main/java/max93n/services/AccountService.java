package max93n.services;

import max93n.models.Account;

public interface AccountService {
    void add(Account account);
    Account getById(Long id);
    Account getByName(String name);
}
