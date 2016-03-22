package max93n.services.impl;

import max93n.entities.Account;
import max93n.entities.AppTransaction;
import max93n.entities.IncomeTransaction;
import max93n.entities.User;
import max93n.repositories.AccountRepository;
import max93n.services.AccountService;
import max93n.validators.BalanceValidator;
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
    public double getCurrentBalance(Account account) {

        double balance = 0;

        for (IncomeTransaction income : account.getIncomeTransactions()) {

            balance += income.getAmount();
        }
        //TODO: add expense to current balance
        return  balance + account.getInitialBalance();
    }


    @Override
    public double getThisMonthBalance(Account account) {

        return calcSum(account.getIncomeTransactions(), Calendar.MONTH);
        //TODO: add expense to this month balance

    }

    @Override
    public double getThisWeekIncome(Account account) {
        return calcSum(account.getIncomeTransactions(), Calendar.WEEK_OF_MONTH);
    }

    @Override
    public double getThisMonthIncome(Account account) {
        return calcSum(account.getIncomeTransactions(), Calendar.MONTH);
    }


    private double calcSum(List<? extends AppTransaction> transactions, int calType) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        int currentMoment = calender.get(calType);

        double sum = 0.0;

        for (AppTransaction transaction: transactions) {
            calender.setTime(transaction.getDate());
            int transactionMoment = calender.get(calType);

            if (currentMoment != transactionMoment) {
                continue;
            }

            sum += transaction.getAmount();
        }
        return sum;
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
