package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.IncomeTransaction;
import max93n.entities.User;
import max93n.repositories.AccountRepository;
import max93n.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public double getThisMonthBalance(Account account) {

        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        int currentMonth = calender.get(Calendar.MONTH);


        double balance = 0.0;

        for (IncomeTransaction income : account.getIncomeTransactions()) {
            calender.setTime(income.getDate());
            int month = calender.get(Calendar.MONTH);

            if (currentMonth != month) {
                continue;
            }

            balance += income.getAmount();
        }
        //TODO: add expense to this month balance

        return balance;
    }

    @Override
    public double getThisWeekIncome(Account account) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        int currentWeek = calender.get(Calendar.WEEK_OF_MONTH);


        double incomeBalance = 0.0;

        for (IncomeTransaction income : account.getIncomeTransactions()) {
            calender.setTime(income.getDate());
            int week = calender.get(Calendar.WEEK_OF_MONTH);

            if (currentWeek != week) {
                continue;
            }

            incomeBalance += income.getAmount();
        }

        return incomeBalance;
    }

    @Override
    public List<Account> getAllByUser(User user) {
        return accountRepository.findAll();
    }

    @Override
    public void add(Account account) {
        Account a = getByName(account.getName());

        if (a != null) {
            return;
        }

        accountRepository.saveAndFlush(account);
    }

    @Override
    public void save(Account account) {
        accountRepository.saveAndFlush(account);
    }

    @Override
    public void remove(Account account) {
        accountRepository.delete(account.getId());
    }


    @Override
    public Account getByName(String name) {
        return accountRepository.getByName(name);
    }
}
