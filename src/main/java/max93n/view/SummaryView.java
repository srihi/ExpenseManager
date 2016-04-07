package max93n.view;


import max93n.entities.*;
import max93n.lazy.TransactionLazyModel;
import max93n.services.*;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class SummaryView {


    @ManagedProperty("#{accountService}")
    private AccountService accountService;


    @ManagedProperty("#{tagService}")
    private TagService tagService;

    @ManagedProperty("#{transactionService}")
    private TransactionService transactionService;
    private Account account;

    private LazyDataModel<Transaction> lazyDataModel;

    private Transaction selectedTransaction;

    private Date minDate, maxDate;

    private List<Tag> availableTags;

    private String periodFilter;
    private Date beginDate, finishDate;
    private boolean dataRange;

    @PostConstruct
    public void init() {
        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");
        account = accountService.getByName(accountName);

        availableTags = tagService.getAllByUser(account.getUser());



        periodFilter = "All";
        dataRange = false;
        periodFilterChanged();
    }


    public void periodFilterChanged() {
        if (periodFilter.equals("All")) {
            dataRange = false;
            minDate = transactionService.getFirstDateOfTransaction(account);
            maxDate = transactionService.getLastDateOfTransaction(account);

        }
        else if (periodFilter.equals("Date Range")) {
            dataRange = true;
            minDate = beginDate;
            maxDate = finishDate;
        }

        lazyDataModel = new TransactionLazyModel(tagService, transactionService, account, minDate, maxDate);
    }

    public void onRowSelect(SelectEvent event) {
        selectedTransaction = ((Transaction) event.getObject());
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LazyDataModel<Transaction> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<Transaction> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }

    public List<Tag> getAvailableTags() {
        return availableTags;
    }

    public void setAvailableTags(List<Tag> availableTags) {
        this.availableTags = availableTags;
    }


    public String getPeriodFilter() {
        return periodFilter;
    }

    public void setPeriodFilter(String periodFilter) {
        this.periodFilter = periodFilter;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isDataRange() {
        return dataRange;
    }

    public void setDataRange(boolean dataRange) {
        this.dataRange = dataRange;
    }
}