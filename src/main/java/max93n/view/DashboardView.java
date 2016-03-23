package max93n.view;


import max93n.entities.Account;
import max93n.entities.User;
import max93n.services.AccountService;
import max93n.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class DashboardView {

    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{userService}")
    private UserService userService;


    private List<Account> accounts;

    private User currentUser;



    @PostConstruct
    public void init() {
        currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accounts = accountService.getAllByUser(currentUser);
    }

    public double getCurrentBalance(Account account) {
        return accountService.getCurrentBalance(account);
    }


    public double getThisMonthBalance(Account account) {
        return accountService.getThisMonthBalance(account);
    }


    public double getThisWeekIncome(Account account) {
        return accountService.getThisWeekIncome(account);
    }


    public double getThisMonthIncome(Account account) {
        return accountService.getThisMonthIncome(account);
    }

    public double getTodayExpense(Account account) {
        return accountService.getTodayExpense(account);
    }

    public double getThisWeekExpense(Account account) {
        return accountService.getThisWeekExpense(account);
    }

    public double getThisMonthExpense(Account account) {
        return accountService.getThisMonthExpense(account);
    }


    public String goToIncomeTransactionView(String accountName) {
        return "transaction?account-name=" + accountName + "&type=income&faces-redirect=true";
    }

    public String goToExpanseTransactionView(String accountName) {
        return "transaction?account-name=" + accountName + "&type=expense&faces-redirect=true";
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}