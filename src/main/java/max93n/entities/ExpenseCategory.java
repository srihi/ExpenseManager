package max93n.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String category;
    private String subCategory;


    @OneToMany(mappedBy = "expenseCategory", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<ExpenseTransaction> getExpenseTransactions() {
        return expenseTransactions;
    }

    public void setExpenseTransactions(List<ExpenseTransaction> expenseTransactions) {
        this.expenseTransactions = expenseTransactions;
    }
}
