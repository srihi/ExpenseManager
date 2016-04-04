package max93n.lazy;

import max93n.entities.Account;
import max93n.entities.ExpenseTag;
import max93n.entities.ExpenseTransaction;
import max93n.entities.Tag;
import max93n.services.ExpenseTransactionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;


public class LazyExpenseTransactionDataModel extends LazyDataModel<ExpenseTransaction> {

    ExpenseTransactionService expenseTransactionService;

    private Date minDate;
    private Date maxDate;
    private Account account;
    private Map<String, Object> filters;

    private List<ExpenseTransaction> transactions;

    public LazyExpenseTransactionDataModel() {
    }

    public LazyExpenseTransactionDataModel(ExpenseTransactionService expenseTransactionService, Account account, Date minDate, Date maxDate) {
        this.expenseTransactionService = expenseTransactionService;
        this.account = account;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

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

        Specification<ExpenseTransaction> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (filters != null) {
                if (filters.containsKey("expenseCategory.category")) {
                    String categoryFilterValue = filters.get("expenseCategory.category").toString();
                    predicates.add(cb.and(cb.like(root.get("expenseCategory").get("category"), "%" + categoryFilterValue + "%")));
                }

                if (filters.containsKey("expenseTags") && ((String[]) filters.get("expenseTags")).length > 0) {
                    //TODO: add tags to filter
//                    String[] tagsArr = (String[]) filters.get("expenseTags");
//                                predicates.add(cb.and(cb.equal(root.<List<ExpenseTag>>get("expenseTags").<Tag>get("tag").get("name"), tagsArr)));

                }
                predicates.add(cb.and(cb.between(root.<Date>get("date"),
                        minDate, maxDate)));
                predicates.add(cb.and(cb.equal(root.<Account>get("account"), account)));
            }

//            List<String> ids = new ArrayList<>();
//            ids.add("Client");
//            Root<ExpenseTag> child = query.from(ExpenseTag.class);
//            Expression<String> expression = child.<Tag>get("expenseTag").get("name");
//            Predicate p = expression.in(ids);
//            predicates.add(cb.and(p));


            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicatesArray));
        };
        int size = (int) expenseTransactionService.getWithSpecificationCount(specification);
        this.setRowCount(size);
        transactions = expenseTransactionService.getWithSpecification(specification, request);
        return transactions;
    }

    public ExpenseTransactionService getExpenseTransactionService() {
        return expenseTransactionService;
    }

    public void setExpenseTransactionService(ExpenseTransactionService expenseTransactionService) {
        this.expenseTransactionService = expenseTransactionService;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public List<ExpenseTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ExpenseTransaction> transactions) {
        this.transactions = transactions;
    }
}
