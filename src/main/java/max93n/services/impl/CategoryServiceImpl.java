package max93n.services.impl;

import max93n.entities.Category;
import max93n.repositories.CategoryRepository;
import max93n.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.AssociationOverride;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

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

    @Override
    public void add(Category category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void save(Category category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void remove(Category category) {
        categoryRepository.delete(category.getId());
    }

}
