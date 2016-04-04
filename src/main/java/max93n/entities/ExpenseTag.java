package max93n.entities;


import javax.persistence.*;

@Entity
public class ExpenseTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_transaction_id", nullable = false)
    private ExpenseTransaction expenseTransaction;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag expenseTag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpenseTransaction getExpenseTransaction() {
        return expenseTransaction;
    }

    public void setExpenseTransaction(ExpenseTransaction expenseTransaction) {
        this.expenseTransaction = expenseTransaction;
    }

    public Tag getExpenseTag() {
        return expenseTag;
    }

    public void setExpenseTag(Tag expenseTag) {
        this.expenseTag = expenseTag;
    }
}
