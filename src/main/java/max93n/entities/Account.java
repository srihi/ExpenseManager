package max93n.entities;


import javax.persistence.*;
import java.util.List;

@Entity
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;
    private double initialBalance;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<IncomeTransaction> incomeTransactions;


    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double balance) {
        this.initialBalance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<IncomeTransaction> getIncomeTransactions() {
        return incomeTransactions;
    }

    public void setIncomeTransactions(List<IncomeTransaction> incomeTransactions) {
        this.incomeTransactions = incomeTransactions;
    }
}
