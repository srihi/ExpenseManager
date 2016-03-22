package max93n.services.impl;


import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseSubCategory;
import max93n.repositories.ExpenseCategoryRepository;
import max93n.services.ExpenseCategoryService;
import max93n.services.ExpenseSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("expenseCategoryService")
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    ExpenseSubCategoryService expenseSubCategoryService;


    @Override
    public ExpenseCategory getByCategory(String category) {
        return expenseCategoryRepository.getByCategory(category);
    }

    @Override
    public List<ExpenseCategory> getAll() {
        return expenseCategoryRepository.findAll();
    }

    @Override
    public void add(String category, String subCategory) {

        ExpenseCategory expenseCategory = expenseCategoryRepository.getByCategory(category);

        if (expenseCategory == null) {
            // add new expenseCategory
            expenseCategory = new ExpenseCategory();
            expenseCategory.setCategory(category);

            expenseCategoryRepository.saveAndFlush(expenseCategory);

            expenseSubCategoryService.add(expenseCategory, subCategory);

        }
    }

    @Override
    public void edit(ExpenseCategory expenseCategory) {
        if (expenseCategory == null) {
            return;
        }
        expenseCategoryRepository.saveAndFlush(expenseCategory);
    }

    @Override
    public void remove(ExpenseCategory expenseCategory) {
        if (expenseCategory == null) {
            return;
        }
        expenseCategoryRepository.delete(expenseCategory.getId());
    }

}
