package max93n.services.impl;


import max93n.models.account.IncomeCategory;
import max93n.repositories.IncomeCategoryRepository;
import max93n.services.IncomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("incomeCategoryService")
public class IncomeCategoryServiceImpl implements IncomeCategoryService{

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Override
    public IncomeCategory getByCategory(String category) {
        return incomeCategoryRepository.getByCategory(category);
    }

    @Override
    public List<IncomeCategory> getAll() {
        return incomeCategoryRepository.findAll();
    }

    @Override
    public void add(IncomeCategory incomeCategory) {
        incomeCategoryRepository.saveAndFlush(incomeCategory);
    }
}
