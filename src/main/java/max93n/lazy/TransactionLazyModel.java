package max93n.lazy;

import max93n.entities.Account;
import max93n.entities.Transaction;
import max93n.services.TransactionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;


public class TransactionLazyModel extends LazyDataModel<Transaction> {

    TransactionService transactionService;

    private Date minDate;
    private Date maxDate;
    private Account account;
    private Map<String, Object> filters;

    private List<Transaction> transactions;

    public TransactionLazyModel() {
    }

    public TransactionLazyModel(TransactionService transactionService, Account account, Date minDate, Date maxDate) {
        this.transactionService = transactionService;
        this.account = account;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    @Override
    public Transaction getRowData(String rowKey) {
        for (Transaction transaction : transactions) {
            if (rowKey.equals(String.valueOf(transaction.getId()))) {
                return transaction;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Transaction expenseTransaction) {
        return expenseTransaction.getId();
    }

    @Override
    public List<Transaction> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                  Map<String, Object> f) {

        PageRequest request = new PageRequest(first / pageSize, pageSize);

        filters = f;

        Specification<Transaction> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (filters != null) {
                if (filters.containsKey("expenseCategory.category")) {
                    String categoryFilterValue = filters.get("expenseCategory.category").toString();
                    predicates.add(cb.and(cb.like(root.get("expenseCategory").get("category"), "%" + categoryFilterValue + "%")));
                }

//                if (filters.containsKey("expenseTags") && ((String[]) filters.get("expenseTags")).length > 0) {
                //TODO: add tags to filter
//                    String[] tagsArr = (String[]) filters.get("expenseTags");
//                                predicates.add(cb.and(cb.equal(root.<List<ExpenseTag>>get("expenseTags").<Tag>get("tag").get("name"), tagsArr)));

//                }
                predicates.add(cb.and(cb.between(root.<Date>get("date"),
                        minDate, maxDate)));
                predicates.add(cb.and(cb.equal(root.<Account>get("account"), account)));
            }


            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicatesArray));
        };
//        int size = (int) expenseTransactionService.getWithSpecificationCount(specification);
//        this.setRowCount(size);
//        transactions = expenseTransactionService.getWithSpecification(specification, request);
        transactions = transactionService.getAllByAccount(account);
        this.setRowCount(transactions.size());
        return transactions;
    }

}