package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.ExpenseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseTagRepository extends JpaRepository<ExpenseTag, Long> {

    @Query("select e from ExpenseTag e where e.expenseTransaction.account = :account")
    List<ExpenseTag> getByAccount(@Param("account") Account account);


}
