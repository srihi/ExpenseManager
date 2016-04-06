package max93n.services;

import max93n.entities.Category;

import java.util.List;

public interface CategoryService {
    Category getByCategoryName(String name);

    List<Category> getCategories();

    List<Category> getIncomeSubCategoriesEscapeInitial();

    void add(Category category);

    void save(Category category);

    void remove(Category category);


}
