package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.ExpenseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseTagRepository extends JpaRepository<ExpenseTag, Long> {

    @Query(value = "select e.tag, min(e.expenseTransaction.date), max(e.expenseTransaction.date)," +
            " sum(e.expenseTransaction.amount)" +
            " from ExpenseTag e " +
            " where e.expenseTransaction.account = :account" +
            " group by e.tag" +
            " order by min(e.expenseTransaction.date)")
    List<Object[]> getSumByTagsAllPeriod(@Param("account") Account account);


}
