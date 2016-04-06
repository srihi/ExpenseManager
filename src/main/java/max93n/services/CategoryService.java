package max93n.services;

import max93n.entities.Category;

import java.util.List;

public interface CategoryService {

    Category getByCategoryName(String name);

    List<Category> getCategories();

    List<Category> getIncomeSubCategoriesEscapeInitial();

    List<Category> getExpenseSubCategories();
    List<Category> getExpenseCategories();

    void add(Category category);
    void add(String categoryName, String subCategoryName);
    void edit(Category category);

    void save(Category category);

    void remove(Category category);
}
