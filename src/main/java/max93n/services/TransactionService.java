package max93n.services;

import max93n.entities.Account;
import max93n.entities.Transaction;
import max93n.enums.TransactionType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void add(Transaction transaction);
    void save(Transaction transaction);
    void remove(Transaction transaction);

    Transaction getInitial(Account account);

    List<Transaction> getAllByAccount(Account account);

    Date getFirstDateOfTransaction(Account account);
    Date getLastDateOfTransaction(Account account);


    List<Transaction> getWithSpecification(Specification specification, PageRequest request);
    long getWithSpecificationCount(Specification specification);


    List<Transaction> getIncomeTransactionByAccount(Account account);
    List<Transaction> getExpenseTransactionByAccount(Account account);



    List<Object[]> getByWeeks(Account account);
    List<Object[]> getByMonths(Account account);
    List<Object[]> getByYears(Account account);
    List<Object[]> getByAllPeriod(Account account, TransactionType transactionType);

    List<Object[]> getSumGroupedByMonthsOfYear(Account account, TransactionType transactionType);
}
