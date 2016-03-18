package max93n.services.impl;

import max93n.models.Account;
import max93n.repositories.AccountRepository;
import max93n.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void add(Account account) {
        accountRepository.saveAndFlush(account);
    }

    @Override
    public Account getById(Long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account getByName(String name) {
        return accountRepository.getByName(name);
    }
}
