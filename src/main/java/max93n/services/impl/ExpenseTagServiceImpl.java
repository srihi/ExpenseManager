package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.ExpenseTag;
import max93n.repositories.ExpenseTagRepository;
import max93n.services.ExpenseTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("expenseTagService")
public class ExpenseTagServiceImpl implements ExpenseTagService{

    @Autowired
    private ExpenseTagRepository expenseTagRepository;

    @Override
    public void add(ExpenseTag expenseTag) {
        expenseTagRepository.saveAndFlush(expenseTag);
    }

    @Override
    public List<Object[]> getSumByTagsAllPeriod(Account account) {
        return expenseTagRepository.getSumByTagsAllPeriod(account);
    }
}
