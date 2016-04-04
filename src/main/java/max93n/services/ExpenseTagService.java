package max93n.services;

import max93n.entities.Account;
import max93n.entities.ExpenseTag;

import java.util.List;

public interface ExpenseTagService {
    void add(ExpenseTag expenseTag);
    List<ExpenseTag> getByAccount(Account account);

}
