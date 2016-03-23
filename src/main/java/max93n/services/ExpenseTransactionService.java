package max93n.services;

import max93n.entities.Account;
import max93n.entities.ExpenseTransaction;

import java.util.List;

public interface ExpenseTransactionService {

    List<ExpenseTransaction> getAllByAccount(Account account);
    void add(ExpenseTransaction transaction);

}
