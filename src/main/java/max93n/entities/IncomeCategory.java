package max93n.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class IncomeCategory implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "income_category_id")
    private Long id;

    @Column(unique = true)
    private String category;

    public IncomeCategory() {
    }

    public IncomeCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
