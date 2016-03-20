package max93n.repositories;

import max93n.models.user.Role;
import max93n.models.user.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>{

    @Query("select r from Role r where r.role = :role")
    Role findByRoleName(@Param("role") RoleEnum role);

}
