package max93n.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String category;

    @OneToMany(mappedBy = "expenseCategory", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ExpenseSubCategory> subCategory;

    @OneToMany(mappedBy = "expenseCategory", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ExpenseTransaction> expenseTransactions;


    public ExpenseCategory() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ExpenseTransaction> getExpenseTransactions() {
        return expenseTransactions;
    }

    public void setExpenseTransactions(List<ExpenseTransaction> expenseTransactions) {
        this.expenseTransactions = expenseTransactions;
    }

    public List<ExpenseSubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<ExpenseSubCategory> subCategory) {
        this.subCategory = subCategory;
    }
}
