package max93n.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ExpenseTag> expenseTags;

    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<IncomeTag> incomeTags;


    public Tag() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String tag) {
        this.name = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExpenseTag> getExpenseTags() {
        return expenseTags;
    }

    public void setExpenseTags(List<ExpenseTag> expenseTags) {
        this.expenseTags = expenseTags;
    }

    public List<IncomeTag> getIncomeTags() {
        return incomeTags;
    }

    public void setIncomeTags(List<IncomeTag> incomeTags) {
        this.incomeTags = incomeTags;
    }
}

