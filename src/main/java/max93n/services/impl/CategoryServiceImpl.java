package max93n.services.impl;

import max93n.entities.Category;
import max93n.repositories.CategoryRepository;
import max93n.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.AssociationOverride;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category getByCategoryName(String name) {
        return categoryRepository.getByCategoryName(name);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.getCategories();
    }

    @Override
    public List<Category> getIncomeSubCategoriesEscapeInitial() {
        return categoryRepository.getIncomeSubCategoriesEscapeInitial();
    }

}
