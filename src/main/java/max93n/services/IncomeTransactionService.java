package max93n.services;

import max93n.entities.Account;
import max93n.entities.IncomeTransaction;

import java.util.Date;
import java.util.List;

public interface IncomeTransactionService {


    IncomeTransaction getInitial(Account account);

    List<IncomeTransaction> getAllByAccount(Account account);
    void add(IncomeTransaction transaction);
    void save(IncomeTransaction transaction);

}
