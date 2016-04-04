package max93n.view;


import max93n.entities.*;
import max93n.lazy.LazyExpenseTransactionDataModel;
import max93n.services.*;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
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

    @ManagedProperty("#{incomeTransactionService}")
    private IncomeTransactionService incomeTransactionService;


    @ManagedProperty("#{expenseTagService}")
    private ExpenseTagService expenseTagService;

    @ManagedProperty("#{tagService}")
    private TagService tagService;



    private String periodFilter;



    private Account account;


    private ExpenseTransaction selectedTransaction;

    private Map<String, Object> filters;

    private List<String> availableTagNames;

    private Date minDate, maxDate;

    private Date beginDate, finishDate;

    private List<String> tabKeys;
    private Map<String, LazyDataModel<ExpenseTransaction>> lazyDataModelMap;

    private boolean dataRange;

    @PostConstruct
    public void init() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        beginDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        finishDate = calendar.getTime();

        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        account = accountService.getByName(accountName);


        List<Tag> tags = tagService.getAllByUser(account.getUser());
        availableTagNames = new ArrayList<>(tags.size());
        for (Tag expenseTag : tags) {
            availableTagNames.add(expenseTag.getName());
        }

        periodFilter = "All";
        periodFilterChanged();

    }


    public void onRowSelect(SelectEvent event) {
        selectedTransaction = ((ExpenseTransaction) event.getObject());
    }


    public void periodFilterChanged() {
        lazyDataModelMap = new LinkedHashMap<>();

        if (periodFilter.equals("All")) {
            dataRange = false;
            minDate = expenseTransactionService.getFirstDateOfExpense(account);
            maxDate = expenseTransactionService.getLastDateOfExpense(account);

            LazyDataModel<ExpenseTransaction> lazyModel = new LazyExpenseTransactionDataModel(expenseTransactionService, account, minDate, maxDate);

            lazyDataModelMap.put("All", lazyModel);

        }
        else if (periodFilter.equals("Date Range")) {
            dataRange = true;
            if (beginDate != null || finishDate != null) {
                LazyDataModel<ExpenseTransaction> lazyModel = new LazyExpenseTransactionDataModel(expenseTransactionService, account, beginDate, finishDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                lazyDataModelMap.put(format.format(beginDate) + " - " + format.format(finishDate), lazyModel);
            }
        }

        tabKeys = new ArrayList<>(lazyDataModelMap.keySet());
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


    public ExpenseTransaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(ExpenseTransaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public List<String> getAvailableTagNames() {
        return availableTagNames;
    }

    public void setAvailableTagNames(List<String> availableTagNames) {
        this.availableTagNames = availableTagNames;
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

    public List<String> getTabKeys() {
        return tabKeys;
    }

    public void setTabKeys(List<String> tabKeys) {
        this.tabKeys = tabKeys;
    }

    public Map<String, LazyDataModel<ExpenseTransaction>> getLazyDataModelMap() {
        return lazyDataModelMap;
    }

    public void setLazyDataModelMap(Map<String, LazyDataModel<ExpenseTransaction>> lazyDataModelMap) {
        this.lazyDataModelMap = lazyDataModelMap;
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

    public IncomeTransactionService getIncomeTransactionService() {
        return incomeTransactionService;
    }

    public void setIncomeTransactionService(IncomeTransactionService incomeTransactionService) {
        this.incomeTransactionService = incomeTransactionService;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}



