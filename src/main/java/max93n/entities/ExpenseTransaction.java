package max93n.entities;


import javax.persistence.*;
import java.util.List;

@Entity
public class ExpenseTransaction extends AppTransaction{


    @ManyToOne
    @JoinColumn(name="expense_category_id", nullable = false)
    private ExpenseCategory expenseCategory;

    @OneToMany(mappedBy = "expenseTransaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ExpenseTag> expenseTags;


    public ExpenseTransaction() {
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public List<ExpenseTag> getExpenseTags() {
        return expenseTags;
    }

    public void setExpenseTags(List<ExpenseTag> expenseTags) {
        this.expenseTags = expenseTags;
    }
}
