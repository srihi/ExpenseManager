package max93n.services.impl;


import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseSubCategory;
import max93n.repositories.ExpenseSubCategoryRepository;
import max93n.services.ExpenseSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("expenseSubCategoryService")
public class ExpenseSubCategoryServiceImpl implements ExpenseSubCategoryService{

    @Autowired
    private ExpenseSubCategoryRepository expenseSubCategoryRepository;


    @Override
    public ExpenseSubCategory getBySubCategory(String subCategory) {
        return expenseSubCategoryRepository.getBySubCategory(subCategory);
    }

    @Override
    public void add(ExpenseCategory expenseCategory, String subCategory) {

        ExpenseSubCategory expenseSubCategory = expenseSubCategoryRepository.getBySubCategory(subCategory);

        if (expenseSubCategory == null) {
            // add new expenseSubCategory
            expenseSubCategory = new ExpenseSubCategory();
            expenseSubCategory.setSubCategory(subCategory);
            expenseSubCategory.setExpenseCategory(expenseCategory);

            expenseSubCategoryRepository.saveAndFlush(expenseSubCategory);
        }
    }
}
