package max93n.repositories;

import max93n.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);
}
