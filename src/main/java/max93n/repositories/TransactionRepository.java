package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {


    @Query("select t from Transaction t where t.account = :account and t.category.name = 'Initial Balance'")
    Transaction getInitial(@Param("account") Account account);


    @Query("select min(e.date) from Transaction e where e.account = :account")
    Date getFirstDateOfTransaction(@Param("account") Account account);

    @Query("select max(e.date) from Transaction e where e.account = :account")
    Date getLastDateOfTransaction(@Param("account") Account account);


    @Query("select t from Transaction t where t.account = :account")
    List<Transaction> getAllByAccount(@Param("account") Account account);

    @Query("select t from Transaction t where t.account = :account and t.transactionType = 'INCOME'")
    List<Transaction> getIncomeTransactionByAccount(@Param("account") Account account);

    @Query("select t from Transaction t where t.account = :account and t.transactionType = 'EXPENSE'")
    List<Transaction> getExpenseTransactionByAccount(@Param("account") Account account);
}
