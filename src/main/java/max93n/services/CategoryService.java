package max93n.services;

import max93n.entities.Category;

import java.util.List;

public interface CategoryService {
    Category getByCategoryName(String name);

    List<Category> getCategories();
//    List<Category> getSubCategories(Category category);


}
