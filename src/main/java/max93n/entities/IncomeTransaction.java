package max93n.entities;


import javax.persistence.*;

@Entity
public class IncomeTransaction extends AppTransaction {

    @ManyToOne
    @JoinColumn(name = "income_category_id", nullable = false)
    private IncomeCategory incomeCategory;

    public IncomeTransaction() {
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

}
