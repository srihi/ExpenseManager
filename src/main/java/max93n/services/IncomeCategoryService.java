package max93n.services;

import max93n.models.account.IncomeCategory;

import java.util.List;

public interface IncomeCategoryService {

    IncomeCategory getByCategory(String category);
    List<IncomeCategory> getAll();
    void add(IncomeCategory incomeCategory);
    void remove(IncomeCategory incomeCategory);
}
