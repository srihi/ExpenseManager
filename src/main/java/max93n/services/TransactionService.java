package max93n.services;

import max93n.entities.Account;
import max93n.entities.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void add(Transaction transaction);
    void save(Transaction transaction);

    Transaction getInitial(Account account);

    List<Transaction> getAllByAccount(Account account);

    Date getFirstDateOfTransaction(Account account);
    Date getLastDateOfTransaction(Account account);


    List<Transaction> getWithSpecification(Specification specification, PageRequest request);
    long getWithSpecificationCount(Specification specification);


    List<Transaction> getIncomeTransactionByAccount(Account account);
    List<Transaction> getExpenseTransactionByAccount(Account account);

}
