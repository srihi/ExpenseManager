package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.IncomeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IncomeTrancationRepository extends JpaRepository<IncomeTransaction, Long>{


    @Query("select i from IncomeTransaction i where i.account = :account and i.incomeCategory.category = 'Initial Balance'")
    IncomeTransaction getInitial(@Param("account") Account account);

    @Query("select i from IncomeTransaction i where i.account = :account")
    List<IncomeTransaction> getAllByAccount(@Param("account") Account account);
}
