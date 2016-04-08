package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.Transaction;
import max93n.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {


    @Modifying
    @Query("delete from Transaction  t where t.id = :id")
    void removeById(@Param("id") Long id);

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




    @Query(value = "select week(e.date),min(e.date), max(e.date), sum(e.amount), e.category.parent" +
            " from Transaction e " +
            " where e.account = :account and e.category.parent.name <> 'Income'" +
            " group by week(e.date), e.category.parent.name" +
            " order by min(e.date)")
    List<Object[]> getByWeeks(@Param("account") Account account);

    @Query(value = "select month(e.date),min(e.date), max(e.date), sum(e.amount), e.category.parent" +
            " from Transaction e " +
            " where e.account = :account and e.category.parent.name <> 'Income'" +
            " group by month(e.date), e.category.parent.name" +
            " order by min(e.date)")
    List<Object[]> getByMonths(@Param("account") Account account);

    @Query(value = "select year(e.date),min(e.date), max(e.date), sum(e.amount), e.category.parent" +
            " from Transaction e " +
            " where e.account = :account and e.category.parent.name <> 'Income'" +
            " group by year(e.date), e.category.parent.name" +
            " order by min(e.date)")
    List<Object[]> getByYears(@Param("account") Account account);

    @Query(value = "select 1,min(e.date), max(e.date), sum(e.amount), e.category.parent" +
            " from Transaction e " +
            " where e.account = :account and e.transactionType = :transactionType and e.category.parent.name <> 'Income'" +
            " group by e.category.parent.name" +
            " order by min(e.date)")
    List<Object[]> getByAllPeriod(@Param("account") Account account,
                                  @Param("transactionType") TransactionType transactionType);

    @Query(value = "select month(e.date), year(e.date), sum(e.amount)" +
            " from Transaction e " +
            " where e.account = :account and e.transactionType = :transactionType" +
            " group by month(e.date)" +
            " order by year(e.date)")
    List<Object[]> getSumGroupedByMonthsOfYear(@Param("account") Account account,
                                               @Param("transactionType") TransactionType transactionType);


}
