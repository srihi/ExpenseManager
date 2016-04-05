package max93n.repositories;

import max93n.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>{



    @Query("select c from Category c where c.name = :name")
    Category getByCategoryName(@Param("name") String name);

    @Query("select c from Category c where c.parent = null and not c.name = 'Initial Balance'")
    List<Category> getCategories();

//    @Query("select c from Category c where c.children = :category")
//    List<Category> getSubCategories(@Param("category") Category category);
}
