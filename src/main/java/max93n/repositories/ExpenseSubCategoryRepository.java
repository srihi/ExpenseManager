package max93n.repositories;

import max93n.entities.ExpenseSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseSubCategoryRepository extends JpaRepository<ExpenseSubCategory, Long>{

    @Query("select e from ExpenseSubCategory e where e.subCategory= :subCategory")
    ExpenseSubCategory getBySubCategory(@Param("subCategory") String subCategory);
}
