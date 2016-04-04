package max93n.view;


import max93n.entities.Account;
import max93n.entities.ExpenseTag;
import max93n.entities.ExpenseTransaction;
import max93n.services.AccountService;
import max93n.services.ExpenseTagService;
import max93n.services.ExpenseTransactionService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.*;
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



    private ExpenseTransaction selectedTransaction;

    private Map<String, Object> filters;

    private List<String> availableTagNames;

    private Date minDate, maxDate;


    private List<String> tabKeys;
    private Map<String, LazyDataModel<ExpenseTransaction>> lazyDataModelMap;



    @PostConstruct
    public void init() {
        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        account = accountService.getByName(accountName);

        List<ExpenseTag> expenseTags = expenseTagService.getByAccount(account);
        availableTagNames = new ArrayList<>(expenseTags.size());
        for (ExpenseTag expenseTag : expenseTags) {
            availableTagNames.add(expenseTag.getTag().getName());
        }

        periodFilter = "All";
        periodFilterChanged();
    }

    private LazyDataModel<ExpenseTransaction> generateLazyModel() {
        return  new LazyDataModel<ExpenseTransaction>() {

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
            public List<ExpenseTransaction> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                 Map<String, Object> f) {

                PageRequest request = new PageRequest(first / pageSize, pageSize);

                filters = f;

                Specification<ExpenseTransaction> specification = new Specification<ExpenseTransaction>() {
                    @Override
                    public Predicate toPredicate(Root<ExpenseTransaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                        List<Predicate> predicates = new ArrayList<>();


                        if (filters != null) {

                            if (filters.containsKey("expenseCategory.category")) {
                                String categoryFilterValue = filters.get("expenseCategory.category").toString();
                                predicates.add(cb.and(cb.like(root.get("expenseCategory").get("category"), "%" + categoryFilterValue + "%")));
                            }


                            if (filters.containsKey("expenseTags") && ((String[]) filters.get("expenseTags")).length > 0) {
                                //TODO: add tags to filter
//                                String [] tagsArr = (String[]) filters.get("expenseTags");
//                                predicates.add(cb.and(cb.equal(root.<List<ExpenseTag>>get("expenseTags").<Tag>get("tag").get("name"), tagsArr)));

                            }


                            predicates.add(cb.and(cb.between(root.<Date>get("date"),
                                    minDate, maxDate)));

                            predicates.add(cb.and(cb.equal(root.<Account>get("account"), account)));
                        }


                        Predicate[] predicatesArray = new Predicate[predicates.size()];
                        return cb.and(predicates.toArray(predicatesArray));

                    }
                };

                this.setRowCount((int) expenseTransactionService.getWithSpecificationCount(specification));
                transactions = expenseTransactionService.getWithSpecification(specification, request);
                return transactions;
            }
        };
    }


    public void onRowSelect(SelectEvent event) {

        selectedTransaction = ((ExpenseTransaction) event.getObject());
    }


    public void periodFilterChanged() {
        lazyDataModelMap = new LinkedHashMap<>();

        if (periodFilter.equals("All")) {
            minDate = expenseTransactionService.getFirstDateOfExpense(account);
            maxDate = expenseTransactionService.getLastDateOfExpense(account);
            LazyDataModel<ExpenseTransaction> lazyModel = generateLazyModel();

            lazyDataModelMap.put("All", lazyModel);

        }
        else if (periodFilter.equals("Weekly")) {

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
}
