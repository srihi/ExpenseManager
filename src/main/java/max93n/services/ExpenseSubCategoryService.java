package max93n.services;

import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseSubCategory;

public interface ExpenseSubCategoryService {


    ExpenseSubCategory getBySubCategory(String subCategory);
    void add(ExpenseCategory expenseCategory, String subCategory);
}
