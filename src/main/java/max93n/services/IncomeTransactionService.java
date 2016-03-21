package max93n.services;

import max93n.entities.Account;
import max93n.entities.IncomeTransaction;

import java.util.List;

public interface IncomeTransactionService {

    List<IncomeTransaction> getAllByAccount(Account account);
    void add(IncomeTransaction transaction);

}
