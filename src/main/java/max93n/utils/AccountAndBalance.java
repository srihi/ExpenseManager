package max93n.utils;

import max93n.entities.Account;

public class AccountAndBalance {

    private Account account;
    private double balance;


    public AccountAndBalance(Account account, double balance) {
        this.account = account;
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
