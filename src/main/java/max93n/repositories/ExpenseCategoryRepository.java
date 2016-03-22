package max93n.repositories;

import max93n.entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory,Long> {

    @Query("select e from ExpenseCategory e where e.category = :category")
    ExpenseCategory getByCategory(@Param("category") String category);
}
