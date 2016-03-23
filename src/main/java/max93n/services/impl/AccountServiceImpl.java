package max93n.services.impl;

import max93n.entities.*;
import max93n.repositories.AccountRepository;
import max93n.services.AccountService;
import max93n.services.IncomeCategoryService;
import max93n.services.IncomeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    IncomeCategoryService incomeCategoryService;

    @Autowired
    IncomeTransactionService incomeTransactionService;


    @Override
    public double getCurrentBalance(Account account) {

        double balance = 0;

        for (IncomeTransaction income : account.getIncomeTransactions()) {

            balance += income.getAmount();
        }
        for (ExpenseTransaction expense : account.getExpenseTransactions()) {

            balance -= expense.getAmount();
        }

        return  balance;
    }


    @Override
    public double getThisMonthBalance(Account account) {
        double balance = calcSum(account.getIncomeTransactions(), Calendar.MONTH);
        balance -= calcSum(account.getExpenseTransactions(), Calendar.MONTH);
        return balance;
    }

    @Override
    public double getThisWeekIncome(Account account) {
        return calcSum(account.getIncomeTransactions(), Calendar.WEEK_OF_MONTH);
    }

    @Override
    public double getThisMonthIncome(Account account) {
        return calcSum(account.getIncomeTransactions(), Calendar.MONTH);
    }

    @Override
    public double getTodayExpense(Account account) {
        return calcSum(account.getExpenseTransactions(), Calendar.DATE);
    }

    @Override
    public double getThisWeekExpense(Account account) {
        return calcSum(account.getExpenseTransactions(), Calendar.WEEK_OF_MONTH);
    }

    @Override
    public double getThisMonthExpense(Account account) {
        return calcSum(account.getExpenseTransactions(), Calendar.MONTH);
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
    public boolean add(Account account, double initialBalance) {
        Account a = getByName(account.getName());

        if (a != null) {
            return false;
        }
        accountRepository.saveAndFlush(account);

        IncomeTransaction incomeTransaction = new IncomeTransaction();
        incomeTransaction.setDate(new Date());
        incomeTransaction.setAmount(initialBalance);
        incomeTransaction.setPayer("Self");
        IncomeCategory incomeCategory = incomeCategoryService.getByCategory("Initial Balance");
        incomeTransaction.setIncomeCategory(incomeCategory);
        incomeTransaction.setPaymentMethod("Cash");
        incomeTransaction.setDescription("Initial Balance");
        incomeTransaction.setAccount(account);
        incomeTransactionService.add(incomeTransaction);
        return true;
    }

    @Override
    public void save(Account account, double initialBalance) {
        accountRepository.saveAndFlush(account);
        IncomeTransaction incomeTransaction= incomeTransactionService.getInitial(account);
        incomeTransaction.setAmount(initialBalance);
        incomeTransactionService.save(incomeTransaction);
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
