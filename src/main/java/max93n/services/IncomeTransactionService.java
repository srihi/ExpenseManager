package max93n.services;

import max93n.models.account.Account;
import max93n.models.account.IncomeTransaction;

import java.util.List;

public interface IncomeTransactionService {

    List<IncomeTransaction> getAllByAccount(Account account);
    void add(IncomeTransaction transaction);

}
