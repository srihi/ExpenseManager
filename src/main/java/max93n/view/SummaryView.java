package max93n.view;


import max93n.entities.Account;
import max93n.entities.AppTransaction;
import max93n.entities.ExpenseTransaction;
import max93n.lazy.LazyExpenseTransactionDataModel;
import max93n.services.AccountService;
import max93n.services.ExpenseTagService;
import max93n.services.ExpenseTransactionService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@ViewScoped
public class SummaryView {


    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{expenseTransactionService}")
    private ExpenseTransactionService expenseTransactionService;


    @ManagedProperty("#{expenseTagService}")
    private ExpenseTagService expenseTagService;


    private String periodFilter;
    //TODO: from date-to date

    private int rows;


    private Account account;

    private LazyDataModel<ExpenseTransaction> lazyModel;


    private ExpenseTransaction selectedTransaction;



    @PostConstruct
    public void init() {
        periodFilter = "All";

        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        account = accountService.getByName(accountName);

        generateByExpenseCategory();


        lazyModel = new LazyDataModel<ExpenseTransaction>() {

            List<ExpenseTransaction> transactions;

            @Override
            public ExpenseTransaction getRowData(String rowKey) {
                for (ExpenseTransaction transaction : transactions) {
                    if (rowKey.equals(String.valueOf(transaction.getId()))) {
                        return transaction;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(ExpenseTransaction expenseTransaction) {
                return expenseTransaction.getId();
            }

            @Override
            public List<ExpenseTransaction> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                this.setRowCount(expenseTransactionService.getCountOfTransactionsByAccount(account));

                PageRequest request = new PageRequest(first / pageSize, pageSize);


                transactions = expenseTransactionService.getBetweenPeriod(
                        account,
                        expenseTransactionService.getFirstDateOfExpense(account),
                        expenseTransactionService.getLastDateOfExpense(account), request);

                return transactions;
            }
        };
    }

    public void onRowSelect(SelectEvent event) {

        selectedTransaction = ((ExpenseTransaction) event.getObject());
    }


    public void periodFilterChanged() {
        generateByExpenseCategory();
    }


    private void generateByExpenseCategory() {

    }


    //TODO:tags
    private void generateByTags() {
//        List<Object[]> list = expenseTagService.getSumByTagsAllPeriod(account);
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }


    public String getPeriodFilter() {
        return periodFilter;
    }

    public void setPeriodFilter(String periodFilter) {
        this.periodFilter = periodFilter;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ExpenseTransactionService getExpenseTransactionService() {
        return expenseTransactionService;
    }

    public void setExpenseTransactionService(ExpenseTransactionService expenseTransactionService) {
        this.expenseTransactionService = expenseTransactionService;
    }

    public ExpenseTagService getExpenseTagService() {
        return expenseTagService;
    }

    public void setExpenseTagService(ExpenseTagService expenseTagService) {
        this.expenseTagService = expenseTagService;
    }

    public LazyDataModel<ExpenseTransaction> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ExpenseTransaction> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public ExpenseTransaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(ExpenseTransaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }
}
