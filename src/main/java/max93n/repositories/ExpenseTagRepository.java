package max93n.repositories;

import max93n.entities.ExpenseTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTagRepository extends JpaRepository<ExpenseTag, Long> {
}
