package max93n.models.account;


import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class IncomeTransaction extends Transaction {

    @Embedded
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
