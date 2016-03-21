package max93n.view;


import max93n.models.account.Account;
import max93n.models.account.IncomeCategory;
import max93n.models.account.IncomeTransaction;
import max93n.services.AccountService;
import max93n.services.IncomeTransactionService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Map;

@ManagedBean
@RequestScoped
public class TransactionView {


    @ManagedProperty("#{incomeTransactionService}")
    private IncomeTransactionService incomeTransactionService;

    @ManagedProperty("#{accountService}")
    private AccountService accountService;



    private Date date;
    private Double amount;
    private String payer;
    private String category;
    private String subCategory;
    private String paymentMethod;
    private String description;

    @PostConstruct
    public void init() {
        date = new Date();
        amount = 0.0;
        paymentMethod = "Cash";

    }


    //TODO: add tags, quantity, unit for measure

    public  void addTransaction() {

        String type = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");
        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        if (type.equals("income")) {
            addIncomeTransaction(accountName);
        }
        else if (type.equals("expense")) {
            addExpenseTransaction(accountName);
        }

    }

    private void addIncomeTransaction(String accountName) {
        IncomeTransaction incomeTransaction = new IncomeTransaction();
        incomeTransaction.setDate(date);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setPayer(payer);
        IncomeCategory incomeCategory = new IncomeCategory(category);
        incomeTransaction.setIncomeCategory(incomeCategory);
        incomeTransaction.setPaymentMethod(paymentMethod);
        incomeTransaction.setDescription(description);
        Account account = accountService.getByName(accountName);
        incomeTransaction.setAccount(account);
        incomeTransactionService.add(incomeTransaction);

    }


    private void addExpenseTransaction(String accountName) {
        //TODO:create addExpenseTransaction
    }


    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public IncomeTransactionService getIncomeTransactionService() {
        return incomeTransactionService;
    }

    public void setIncomeTransactionService(IncomeTransactionService incomeTransactionService) {
        this.incomeTransactionService = incomeTransactionService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
