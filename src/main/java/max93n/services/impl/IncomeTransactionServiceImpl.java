package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.IncomeTransaction;
import max93n.repositories.IncomeTrancationRepository;
import max93n.services.AccountService;
import max93n.services.IncomeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("incomeTransactionService")
public class IncomeTransactionServiceImpl implements IncomeTransactionService {

    @Autowired
    private IncomeTrancationRepository incomeTrancationRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public List<IncomeTransaction> getAllByAccount(Account account) {
        return incomeTrancationRepository.getAllByAccount(account);
    }

    @Override
    public void add(IncomeTransaction transaction) {

        incomeTrancationRepository.saveAndFlush(transaction);

    }


}
