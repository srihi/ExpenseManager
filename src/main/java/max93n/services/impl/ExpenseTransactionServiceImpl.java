package max93n.services.impl;


import max93n.entities.Account;
import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseTransaction;
import max93n.repositories.ExpenseTransactionRepository;
import max93n.services.ExpenseTransactionService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Object[]> getByWeeks(Account account) {
        return expenseTransactionRepository.getByWeeks(account);
    }

    @Override
    public List<Object[]> getByMonths(Account account) {
        return expenseTransactionRepository.getByMonths(account);
    }

    @Override
    public List<Object[]> getByYears(Account account) {
        return expenseTransactionRepository.getByYears(account);
    }

    @Override
    public List<Object[]> getByAllPeriod(Account account) {
        return expenseTransactionRepository.getByAllPeriod(account);
    }

    @Override
    public List<Object[]> getSumGroupedByMonthsOfYear(Account account) {
        return expenseTransactionRepository.getSumGroupedByMonthsOfYear(account);
    }

    @Override
    public void add(ExpenseTransaction transaction) {
        expenseTransactionRepository.saveAndFlush(transaction);
    }

    @Override
    public int getCountOfTransactionsByAccount(Account account) {
        return expenseTransactionRepository.getCountOfTransactionsByAccount(account);
    }

    @Override
    public List<ExpenseTransaction> getBetweenPeriod(Account account, Date start, Date end, PageRequest request) {

        return expenseTransactionRepository.getBetweenPeriod(account, start, end, request);
    }


    @Transactional
    public List<ExpenseTransaction> getWithSpecification(Specification specification, PageRequest request) {
        List<ExpenseTransaction> res = expenseTransactionRepository.findAll(specification, request).getContent();

        for (ExpenseTransaction expenseTransaction  :  res) {
//            expenseTransaction.getExpenseTags().size();
            Hibernate.initialize(expenseTransaction.getExpenseTags());
        }



        return  res;
    }

    public long getWithSpecificationCount(Specification specification) {
        return  expenseTransactionRepository.count(specification);
    }

    @Override
    public Object[] getWeekPeriods(Account account) {
        return expenseTransactionRepository.getWeekPeriods(account);
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
