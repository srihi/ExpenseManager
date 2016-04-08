package max93n.repositories;


import max93n.entities.Account;
import max93n.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long>{


    @Query("select a from Account a where a.name= :name")
    Account getByName(@Param("name") String name);

    @Query("select a from Account a where a.user= :user")
    Account getAllByUser(@Param("user") User user);

    @Modifying
    @Query("delete from Account a where a.id = :id")
    void deleteById(@Param("id") Long id);
}
