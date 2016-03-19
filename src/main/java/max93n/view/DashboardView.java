package max93n.view;


import max93n.models.Account;
import max93n.models.User;
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