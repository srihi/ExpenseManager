package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction, Long> {

    @Query("select e from ExpenseTransaction e where e.account = :account")
    List<ExpenseTransaction> getAllByAccount(@Param("account") Account account);

    @Query(value = "select week(e.date),min(e.date), max(e.date), sum(e.amount), e.expenseCategory" +
            " from ExpenseTransaction e " +
            " where e.account = :account" +
            " group by week(e.date), e.expenseCategory" +
            " order by min(e.date)")
    List<Object[]> getByWeeks(@Param("account") Account account);

    @Query(value = "select month(e.date),min(e.date), max(e.date), sum(e.amount), e.expenseCategory" +
            " from ExpenseTransaction e " +
            " where e.account = :account" +
            " group by month(e.date), e.expenseCategory" +
            " order by min(e.date)")
    List<Object[]> getByMonths(@Param("account") Account account);

    @Query(value = "select year(e.date),min(e.date), max(e.date), sum(e.amount), e.expenseCategory" +
            " from ExpenseTransaction e " +
            " where e.account = :account" +
            " group by year(e.date), e.expenseCategory" +
            " order by min(e.date)")
    List<Object[]> getByYears(@Param("account") Account account);

    @Query(value = "select 1,min(e.date), max(e.date), sum(e.amount), e.expenseCategory" +
            " from ExpenseTransaction e " +
            " where e.account = :account" +
            " group by e.expenseCategory" +
            " order by min(e.date)")
    List<Object[]> getByAllPeriod(@Param("account") Account account);


    @Query(value = "select month(e.date), year(e.date), sum(e.amount)" +
            " from ExpenseTransaction e " +
            " where e.account = :account" +
            " group by month(e.date)" +
            " order by year(e.date)")
    List<Object[]> getSumGroupedByMonthsOfYear(@Param("account") Account account);



    @Query("select min(e.date) from ExpenseTransaction e where e.account = :account")
    Date getFirstDateOfExpense(@Param("account") Account account);

    @Query("select max(e.date) from ExpenseTransaction e where e.account = :account")
    Date getLastDateOfExpense(@Param("account") Account account);

    @Query("select sum(e.amount) from ExpenseTransaction e where e.account = :account and e.expenseCategory = :expenseCategory")
    Double getSumForCategory(@Param("account") Account account, @Param("expenseCategory") ExpenseCategory expenseCategory);

}
