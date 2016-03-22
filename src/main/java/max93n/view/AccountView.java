package max93n.view;


import max93n.entities.Account;
import max93n.entities.User;
import max93n.entities.CurrencyEnum;
import max93n.services.AccountService;
import max93n.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class AccountView {

    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{userService}")
    private UserService userService;

    private String name;
    private String description;
    private String currency;
    private double initialBalance = 0.0;

    private Account currentAccount;

    private List<Account> accounts;

    private User currentUser;

    @PostConstruct
    public void init() {
        currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accounts = accountService.getAllByUser(currentUser);
    }

    public void removeAccount(Account account) {
        accountService.remove(account);
        accounts.remove(account);
    }

    public void createAccount() {
        Account account = new Account();
        account.setName(name);
        account.setDescription(description);
        account.setCurrency(CurrencyEnum.valueOf(currency));
        account.setInitialBalance(initialBalance);

        account.setUser(currentUser);
        accountService.add(account);
        accounts.add(account);
    }

    public void editAccount() {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        currentAccount.setUser(currentUser);
        accountService.save(currentAccount);
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
