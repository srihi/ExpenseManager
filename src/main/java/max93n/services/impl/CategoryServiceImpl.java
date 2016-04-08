package max93n.services.impl;

import max93n.entities.Category;
import max93n.entities.Transaction;
import max93n.repositories.CategoryRepository;
import max93n.services.CategoryService;
import max93n.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.AssociationOverride;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionService transactionService;


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
    public List<Category> getExpenseSubCategories() {
        return categoryRepository.getExpenseSubCategories();
    }

    @Override
    public List<Category> getExpenseCategories() {
        return categoryRepository.getExpenseCategories();
    }

    @Override
    public void add(Category category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void add(String categoryName, String subCategoryName) {
        Category expenseCategory = categoryRepository.getByCategoryName(categoryName);

        if (expenseCategory == null) {
            // add new expenseCategory and subCategory
            expenseCategory = new Category();
            expenseCategory.setName(categoryName);
            expenseCategory.setTransactions(null);

            categoryRepository.saveAndFlush(expenseCategory);

            Category expenseSubCategory = new Category();
            expenseSubCategory.setName(subCategoryName);
            expenseSubCategory.setParent(expenseCategory);

            categoryRepository.saveAndFlush(expenseSubCategory);
        }
        else {
            Category expenseSubCategory = categoryRepository.getByCategoryName(subCategoryName);
            if (expenseSubCategory == null) {
                // add new subCategory

                expenseSubCategory = new Category();
                expenseSubCategory.setName(subCategoryName);
                expenseSubCategory.setParent(expenseCategory);
                categoryRepository.saveAndFlush(expenseSubCategory);
            }
        }
    }

    @Override
    public void edit(Category category) {
        if (category != null) {
            categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public void save(Category category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    @Transactional
    public void remove(Category category) {

        if (category.getTransactions() != null && category.getTransactions().size() > 0) {
            for (Transaction t :
                    category.getTransactions()) {
                transactionService.remove(t);
            }
        }



        if (category.getParent() != null) {
//            Category parent = category.getParent();
//            parent.getChildren().remove(category);
//            categoryRepository.saveAndFlush(parent);
            categoryRepository.removeById(category.getId());
        }
        else {
//            category.setChildren(null);
//            categoryRepository.saveAndFlush(category);
            for (Category c :
                    category.getChildren()) {
                remove(c);
            }
            categoryRepository.removeById(category.getId());
        }
    }
}
