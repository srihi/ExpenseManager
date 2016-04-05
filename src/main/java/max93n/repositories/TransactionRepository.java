package max93n.repositories;

import max93n.entities.Account;
import max93n.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{


    @Query("select t from Transaction t where t.account = :account and t.category.name = 'Initial Balance'")
    Transaction getInitial(@Param("account") Account account);

}
