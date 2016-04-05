package max93n.view;


import max93n.entities.Account;
import max93n.entities.Transaction;
import max93n.entities.User;
import max93n.enums.CurrencyEnum;
import max93n.services.AccountService;
import max93n.services.TransactionService;
import max93n.services.UserService;
import max93n.utils.AccountAndBalance;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class AccountView {

    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{userService}")
    private UserService userService;

    @ManagedProperty("#{transactionService}")
    private TransactionService transactionService;


    private String name;
    private String description;
    private String currency;
    private double initialBalance = 0.0;

    private AccountAndBalance currentAccountAndBalance;

    private User currentUser;

    private List<AccountAndBalance> accountsAndBalance;

    @PostConstruct
    public void init() {
        currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Account> accounts = accountService.getAllByUser(currentUser);
        accountsAndBalance = new ArrayList<>();

        for (Account account : accounts) {
            Transaction transaction = transactionService.getInitial(account);
            double balance = transaction.getAmount();
            accountsAndBalance.add(new AccountAndBalance(account, balance));
        }
    }

    public void removeAccount(AccountAndBalance accountAndBalance) {
        accountService.remove(accountAndBalance.getAccount());
        accountsAndBalance.remove(accountAndBalance);
    }

    public void createAccount() {
        Account account = new Account();
        account.setName(name);
        account.setDescription(description);
        account.setCurrency(CurrencyEnum.valueOf(currency));


        account.setUser(currentUser);
        if (accountService.add(account, initialBalance)) {
            accountsAndBalance.add(new AccountAndBalance(account, initialBalance));
        }
    }

    public void editAccount() {

        //currentAccount.setUser(currentUser);
        accountService.save(currentAccountAndBalance.getAccount(), currentAccountAndBalance.getBalance());
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


    public List<AccountAndBalance> getAccountsAndBalance() {
        return accountsAndBalance;
    }

    public void setAccountsAndBalance(List<AccountAndBalance> accountsAndBalance) {
        this.accountsAndBalance = accountsAndBalance;
    }

    public AccountAndBalance getCurrentAccountAndBalance() {
        return currentAccountAndBalance;
    }

    public void setCurrentAccountAndBalance(AccountAndBalance currentAccountAndBalance) {
        this.currentAccountAndBalance = currentAccountAndBalance;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
