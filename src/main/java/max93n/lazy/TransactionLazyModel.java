package max93n.lazy;

import max93n.entities.Account;
import max93n.entities.Category;
import max93n.entities.Tag;
import max93n.entities.Transaction;
import max93n.enums.TransactionType;
import max93n.services.TagService;
import max93n.services.TransactionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;


public class TransactionLazyModel extends LazyDataModel<Transaction> {

    TransactionService transactionService;
    TagService tagService;

    private Date minDate;
    private Date maxDate;
    private Account account;


    private List<Transaction> transactions;

    public TransactionLazyModel() {
    }

    public TransactionLazyModel(TagService tagService, TransactionService transactionService, Account account, Date minDate, Date maxDate) {
        this.transactionService = transactionService;
        this.tagService = tagService;

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
                                  Map<String, Object> filters) {

        PageRequest request = new PageRequest(first / pageSize, pageSize);


        Specification<Transaction> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.and(cb.between(root.<Date>get("date"), minDate, maxDate)));
            predicates.add(cb.and(cb.equal(root.<Account>get("account"), account)));

            if (filters != null) {
                if (filters.containsKey("category.parent")) {
                    String categoryFilterValue = filters.get("category.parent").toString();
                    predicates.add(cb.like(root.<Category>get("category").<Category>get("parent").<String>get("name"), "%" + categoryFilterValue + "%"));
                }

                if (filters.containsKey("category")) {
                    String categoryFilterValue = filters.get("category").toString();
                    predicates.add(cb.like(root.<Category>get("category").<String>get("name"), "%" + categoryFilterValue + "%"));
                }

                if (filters.containsKey("tags")) {

                    ListJoin<Transaction, Tag> transactionTagListJoin = root.joinList("tags");
                    List<Tag> tags = new ArrayList<>();
                    tags.add(tagService.getByName((String) filters.get("tags")));
                    predicates.add(transactionTagListJoin.in(tags));

                }
            }


            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(predicatesArray));
        };

        int size = (int) transactionService.getWithSpecificationCount(specification);
        this.setRowCount(size);
        transactions = transactionService.getWithSpecification(specification, request);
        return transactions;
    }

}