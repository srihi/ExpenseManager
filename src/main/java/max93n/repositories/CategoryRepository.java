package max93n.repositories;

import max93n.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    @Modifying
    @Query("delete from Category c where c.id = :id")
    void removeById(@Param("id") Long id);


    @Query("select c from Category c where c.name = :name")
    Category getByCategoryName(@Param("name") String name);

    @Query("select c from Category c where c.parent = null and not c.name = 'Initial Balance'")
    List<Category> getCategories();

    @Query("select c from Category c where c.parent.name = 'Income' and not c.name = 'Initial Balance'")
    List<Category> getIncomeSubCategoriesEscapeInitial();

    @Query("select c from Category c where c.parent.name <> 'Income' and c.parent <> null")
    List<Category> getExpenseSubCategories();

    @Query("select c from Category c where c.name <> 'Income' and c.parent = null")
    List<Category> getExpenseCategories();
}
