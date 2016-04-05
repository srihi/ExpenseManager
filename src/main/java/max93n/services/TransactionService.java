package max93n.services;

import max93n.entities.Account;
import max93n.entities.Transaction;

public interface TransactionService {
    void add(Transaction transaction);
    void save(Transaction transaction);

    Transaction getInitial(Account account);

}
