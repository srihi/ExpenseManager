package max93n.services.impl;

import max93n.entities.IncomeTag;
import max93n.repositories.IncomeTagRepository;
import max93n.services.IncomeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("incomeTagService")
public class IncomeTagServiceImpl implements IncomeTagService {

    @Autowired
    private IncomeTagRepository incomeTagRepository;


    @Override
    public void add(IncomeTag incomeTag) {
        incomeTagRepository.saveAndFlush(incomeTag);
    }
}
