package max93n.services.impl;

import max93n.entities.ExpenseTag;
import max93n.repositories.ExpenseTagRepository;
import max93n.services.ExpenseTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("expenseTagService")
public class ExpenseTagServiceImpl implements ExpenseTagService{

    @Autowired
    private ExpenseTagRepository expenseTagRepository;

    @Override
    public void add(ExpenseTag expenseTag) {
        expenseTagRepository.saveAndFlush(expenseTag);
    }
}
