package max93n.repositories;


import max93n.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long>{


    @Query("select a from Account a where a.name= :name")
    Account getByName(@Param("name") String name);

}
