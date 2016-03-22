package max93n.entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExpenseTransaction extends AppTransaction{


    @ManyToOne
    @JoinColumn(name="expense_category_id", nullable = false)
    private ExpenseCategory expenseCategory;

    public ExpenseTransaction() {
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
}
