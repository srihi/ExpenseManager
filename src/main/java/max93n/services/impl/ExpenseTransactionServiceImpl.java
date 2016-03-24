package max93n.services.impl;


import max93n.entities.Account;
import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseTransaction;
import max93n.repositories.ExpenseTransactionRepository;
import max93n.services.ExpenseTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("expenseTransactionService")
public class ExpenseTransactionServiceImpl implements ExpenseTransactionService {

    @Autowired
    private ExpenseTransactionRepository expenseTransactionRepository;


    @Override
    public List<ExpenseTransaction> getAllByAccount(Account account) {
        return expenseTransactionRepository.getAllByAccount(account);
    }

    @Override
    public void add(ExpenseTransaction transaction) {
        expenseTransactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Date getFirstDateOfExpense(Account account) {
        return expenseTransactionRepository.getFirstDateOfExpense(account);
    }

    @Override
    public Date getLastDateOfExpense(Account account) {
        return expenseTransactionRepository.getLastDateOfExpense(account);
    }

    @Override
    public Double getSumFormCategory(Account account, ExpenseCategory expenseCategory) {
        return expenseTransactionRepository.getSumForCategory(account, expenseCategory);
    }
}
