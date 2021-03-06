package max93n.entities;


import javax.persistence.*;

@Entity
public class IncomeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "income_transaction_id", nullable = false)
    private IncomeTransaction incomeTransaction;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag incomeTag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IncomeTransaction getIncomeTransaction() {
        return incomeTransaction;
    }

    public void setIncomeTransaction(IncomeTransaction incomeTransaction) {
        this.incomeTransaction = incomeTransaction;
    }

    public Tag getIncomeTag() {
        return incomeTag;
    }

    public void setIncomeTag(Tag incomeTag) {
        this.incomeTag = incomeTag;
    }
}
