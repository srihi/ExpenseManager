package max93n.services.impl;

import max93n.models.account.Account;
import max93n.models.account.AppTransaction;
import max93n.models.account.IncomeTransaction;
import max93n.repositories.IncomeTrancationRepository;
import max93n.services.IncomeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("incomeTransactionService")
public class IncomeTransactionServiceImpl implements IncomeTransactionService {

    @Autowired
    private IncomeTrancationRepository incomeTrancationRepository;

    @Override
    public List<IncomeTransaction> getAllByAccount(Account account) {
        return incomeTrancationRepository.getAllByAccount(account);
    }

    @Override
    public void add(IncomeTransaction transaction) {
        incomeTrancationRepository.saveAndFlush(transaction);
    }


}
