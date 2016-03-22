package max93n.services;

import max93n.entities.ExpenseCategory;
import max93n.entities.IncomeCategory;

import java.util.List;

public interface ExpenseCategoryService {

    ExpenseCategory getByCategory(String category);
    List<ExpenseCategory> getAll();
    void add(String category, String subCategory);
    void edit(ExpenseCategory expenseCategory);
    void remove(ExpenseCategory expenseCategory);
}
