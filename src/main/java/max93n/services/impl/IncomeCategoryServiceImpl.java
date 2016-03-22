package max93n.services.impl;


import max93n.entities.Account;
import max93n.entities.IncomeCategory;
import max93n.entities.IncomeTransaction;
import max93n.repositories.IncomeCategoryRepository;
import max93n.services.AccountService;
import max93n.services.IncomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("incomeCategoryService")
public class IncomeCategoryServiceImpl implements IncomeCategoryService{

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public IncomeCategory getByCategory(String category) {
        return incomeCategoryRepository.getByCategory(category);
    }

    @Override
    public List<IncomeCategory> getAll() {
        return incomeCategoryRepository.findAll();
    }

    @Override
    public void save(IncomeCategory incomeCategory) {
        incomeCategoryRepository.saveAndFlush(incomeCategory);
    }

    @Override
    public void add(IncomeCategory incomeCategory) {
        incomeCategoryRepository.saveAndFlush(incomeCategory);
    }

    @Override
    public void remove(IncomeCategory incomeCategory) {
        incomeCategoryRepository.delete(incomeCategory.getId());
    }
}
