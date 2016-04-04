package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseTransaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction, Long>, JpaSpecificationExecutor<ExpenseTransaction> {

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


    @Query("select count(e) from ExpenseTransaction  e where  e.account = :account")
    int getCountOfTransactionsByAccount(@Param("account") Account account);


    @Query("select e from ExpenseTransaction e where e.account = :account and  e.date between :startDate and :endDate")
    List<ExpenseTransaction> getBetweenPeriod(@Param("account") Account account,
                                              @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                              Pageable request);


    @Query("select min(e.date) from ExpenseTransaction e where e.account = :account")
    Date getFirstDateOfExpense(@Param("account") Account account);

    @Query("select max(e.date) from ExpenseTransaction e where e.account = :account")
    Date getLastDateOfExpense(@Param("account") Account account);

    @Query("select sum(e.amount) from ExpenseTransaction e where e.account = :account and e.expenseCategory = :expenseCategory")
    Double getSumForCategory(@Param("account") Account account, @Param("expenseCategory") ExpenseCategory expenseCategory);

    @Query("select week(e.date), min(e.date), max(e.date) from ExpenseTransaction  e where e.account = :account " +
            "group by week(e.date) order by e.date")
    Object[] getWeekPeriods(@Param("account") Account account);

}
