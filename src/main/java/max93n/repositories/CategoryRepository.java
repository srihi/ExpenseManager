package max93n.repositories;

import max93n.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>{



    @Query("select c from Category c where c.name = :name")
    Category getByCategoryName(@Param("name") String name);


}
