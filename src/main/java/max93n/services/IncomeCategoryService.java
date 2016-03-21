package max93n.services;

import max93n.entities.IncomeCategory;

import java.util.List;

public interface IncomeCategoryService {

    IncomeCategory getByCategory(String category);
    List<IncomeCategory> getAll();
    void save(IncomeCategory incomeCategory);
    void add(IncomeCategory incomeCategory);
    void remove(IncomeCategory incomeCategory);
}
