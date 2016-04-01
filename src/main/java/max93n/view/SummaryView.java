package max93n.view;


import max93n.entities.Account;
import max93n.entities.AppTransaction;
import max93n.entities.ExpenseTag;
import max93n.entities.ExpenseTransaction;
import max93n.lazy.LazyExpenseTransactionDataModel;
import max93n.services.AccountService;
import max93n.services.ExpenseTagService;
import max93n.services.ExpenseTransactionService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    private Map<String, Object> filters;

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
            public List<ExpenseTransaction> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                 Map<String, Object> f) {
                this.setRowCount(expenseTransactionService.getCountOfTransactionsByAccount(account));

                PageRequest request = new PageRequest(first / pageSize, pageSize);

                filters = f;

                Specification<ExpenseTransaction> specification = new Specification<ExpenseTransaction>() {
                    @Override
                    public Predicate toPredicate(Root<ExpenseTransaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                        Predicate predicate = null;

                        if (filters != null) {
                            Iterator<String> filterIterator = filters.keySet().iterator();
                            while (filterIterator.hasNext()) {
                                String filterProperty = filterIterator.next();
                                Object filterValue = filters.get(filterProperty);
                                if (filterProperty.equals("expenseCategory.category")) {
                                    predicate =
                                            cb.and(
                                                    cb.like(root.get("expenseCategory").get("category"), "%" + filterValue.toString() + "%"),
                                                    cb.between(root.<Date>get("date"),
                                                            expenseTransactionService.getFirstDateOfExpense(account),
                                                            expenseTransactionService.getLastDateOfExpense(account))
                                            );
                                }
                                //TODO:tags
                            }
                        }

                        return predicate;
                    }
                };


                transactions = expenseTransactionService.getWithSpecification(specification, request);

//                transactions = expenseTransactionService.getBetweenPeriod(
//                        account,
//                        expenseTransactionService.getFirstDateOfExpense(account),
//                        expenseTransactionService.getLastDateOfExpense(account), request);

                return  transactions;
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
