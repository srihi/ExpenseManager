package max93n.entities;


import javax.persistence.*;
import java.util.List;

@Entity
public class IncomeTransaction extends AppTransaction {

    @ManyToOne
    @JoinColumn(name = "income_category_id", nullable = false)
    private IncomeCategory incomeCategory;

    @OneToMany(mappedBy = "incomeTransaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IncomeTag> incomeTags;


    public IncomeTransaction() {
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }


    public List<IncomeTag> getIncomeTags() {
        return incomeTags;
    }

    public void setIncomeTags(List<IncomeTag> incomeTags) {
        this.incomeTags = incomeTags;
    }
}
