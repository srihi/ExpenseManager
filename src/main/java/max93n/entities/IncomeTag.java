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
    private Tag tag;


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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
