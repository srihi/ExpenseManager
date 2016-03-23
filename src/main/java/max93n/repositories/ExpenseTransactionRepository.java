package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.ExpenseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction, Long> {

    @Query("select e from ExpenseTransaction e where e.account = :account")
    List<ExpenseTransaction> getAllByAccount(@Param("account") Account account);

}
