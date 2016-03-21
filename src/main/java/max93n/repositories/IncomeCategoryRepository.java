package max93n.repositories;

import max93n.models.account.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {


    @Query("select i from IncomeCategory i where i.category = :category")
    IncomeCategory getByCategory(@Param("category") String category);

}
