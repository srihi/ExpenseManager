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

    @Query("select min(e.date) from ExpenseTransaction e where e.account = :account")
    Date getFirstDateOfExpense(@Param("account") Account account);

    @Query("select max(e.date) from ExpenseTransaction e where e.account = :account")
    Date getLastDateOfExpense(@Param("account") Account account);

    @Query("select sum(e.amount) from ExpenseTransaction e where e.account = :account and e.expenseCategory = :expenseCategory")
    Double getSumForCategory(@Param("account") Account account, @Param("expenseCategory") ExpenseCategory expenseCategory);

}
