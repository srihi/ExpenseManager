package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.IncomeTransaction;
import max93n.repositories.IncomeTrancationRepository;
import max93n.services.AccountService;
import max93n.services.IncomeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("incomeTransactionService")
public class IncomeTransactionServiceImpl implements IncomeTransactionService {

    @Autowired
    private IncomeTrancationRepository incomeTrancationRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public IncomeTransaction getInitial(Account account) {
        return incomeTrancationRepository.getInitial(account);
    }

    @Override
    public List<Object[]> getSumGroupedByMonthsOfYear(Account account) {
        return incomeTrancationRepository.getSumGroupedByMonthsOfYear(account);
    }

    @Override
    public List<IncomeTransaction> getAllByAccount(Account account) {
        return incomeTrancationRepository.getAllByAccount(account);
    }

    @Override
    public void add(IncomeTransaction transaction) {
        incomeTrancationRepository.saveAndFlush(transaction);
    }

    @Override
    public void save(IncomeTransaction transaction) {
        incomeTrancationRepository.saveAndFlush(transaction);
    }

}
