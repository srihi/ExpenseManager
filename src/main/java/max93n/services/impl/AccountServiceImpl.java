package max93n.services.impl;

import max93n.models.Account;
import max93n.repositories.AccountRepository;
import max93n.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void add(Account account) {
        Account a = getByName(account.getName());

        if (a != null) {
            return;
        }

        accountRepository.saveAndFlush(account);
    }

    @Override
    public void save(Account account) {
        accountRepository.saveAndFlush(account);
    }

    @Override
    public void remove(Account account) {
        accountRepository.delete(account.getId());
    }


    @Override
    public Account getByName(String name) {
        return accountRepository.getByName(name);
    }
}
