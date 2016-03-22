package max93n.entities;


import javax.persistence.*;


@Entity
public class ExpenseSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subCategory;

    @ManyToOne()
    @JoinColumn(name = "expense_category", nullable = false)
    private ExpenseCategory expenseCategory;


    public ExpenseSubCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }


    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
}
