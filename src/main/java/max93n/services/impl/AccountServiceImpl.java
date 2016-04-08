package max93n.services.impl;

import max93n.entities.*;
import max93n.enums.TransactionType;
import max93n.repositories.AccountRepository;
import max93n.services.AccountService;
import max93n.services.CategoryService;
import max93n.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;




    @Override
    public double getCurrentBalance(Account account) {

        double balance = 0;

        for (Transaction transaction : account.getTransactions()) {

            if (transaction.getTransactionType() == TransactionType.INCOME) {
                balance += transaction.getAmount();
            }
            else {
                balance -= transaction.getAmount();
            }

        }

        return  balance;
    }


    @Override
    public double getThisMonthBalance(Account account) {

        double balance = calcSum(transactionService.getIncomeTransactionByAccount(account), Calendar.MONTH);
        balance -= calcSum(transactionService.getExpenseTransactionByAccount(account), Calendar.MONTH);
        return balance;
    }

    @Override
    public double getThisWeekIncome(Account account) {
        return calcSum(transactionService.getIncomeTransactionByAccount(account), Calendar.WEEK_OF_MONTH);
    }

    @Override
    public double getThisMonthIncome(Account account) {
        return calcSum(transactionService.getIncomeTransactionByAccount(account), Calendar.MONTH);
    }

    @Override
    public double getTodayExpense(Account account) {
        return calcSum(transactionService.getExpenseTransactionByAccount(account), Calendar.DATE);
    }

    @Override
    public double getThisWeekExpense(Account account) {
        return calcSum(transactionService.getExpenseTransactionByAccount(account), Calendar.WEEK_OF_MONTH);
    }

    @Override
    public double getThisMonthExpense(Account account) {
        return calcSum(transactionService.getExpenseTransactionByAccount(account), Calendar.MONTH);
    }

    private double calcSum(List<Transaction> transactions, int calType) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        int currentMoment = calender.get(calType);

        double sum = 0.0;

        for (Transaction transaction: transactions) {
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


        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setAmount(initialBalance);
        transaction.setPayer("Self");
        Category incomeCategory = categoryService.getByCategoryName("Initial Balance");
        transaction.setCategory(incomeCategory);
        transaction.setPaymentMethod("Cash");
        transaction.setDescription("Initial Balance");
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.INCOME);
        transactionService.add(transaction);
        account.getTransactions().add(transaction);
        return true;
    }

    @Override
    public void save(Account account, double initialBalance) {
        accountRepository.saveAndFlush(account);
        Transaction incomeTransaction= transactionService.getInitial(account);
        incomeTransaction.setAmount(initialBalance);
        transactionService.save(incomeTransaction);
    }

    @Override
    @Transactional
    public void remove(Account account) {
        //refresh
        account = accountRepository.findOne(account.getId());
        for (Transaction t:account.getTransactions()) {
            transactionService.remove(t);
        }

        accountRepository.deleteById(account.getId());
    }


    @Override
    public Account getByName(String name) {
        return accountRepository.getByName(name);
    }
}
