package max93n.services;

import max93n.entities.Account;
import max93n.entities.User;

import java.util.List;

public interface AccountService {


    double getCurrentBalance(Account account);

    double getThisMonthBalance(Account account);

    double getThisWeekIncome(Account account);
    double getThisMonthIncome(Account account);

    double getTodayExpense(Account account);
    double getThisWeekExpense(Account account);
    double getThisMonthExpense(Account account);


    List<Account> getAllByUser(User user);
    boolean add(Account account, double initialBalance);
    void save(Account account, double initialBalance);
    void remove(Account account);
    Account getByName(String name);
}
