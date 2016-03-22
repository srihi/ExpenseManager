package max93n.services;

import max93n.entities.Account;
import max93n.entities.User;

import java.util.List;

public interface AccountService {


    double getCurrentBalance(Account account);

    double getThisMonthBalance(Account account);

    double getThisWeekIncome(Account account);


    List<Account> getAllByUser(User user);
    void add(Account account);
    void save(Account account);
    void remove(Account account);
    Account getByName(String name);
}
