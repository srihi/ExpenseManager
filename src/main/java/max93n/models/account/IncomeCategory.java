package max93n.models.account;

import java.io.Serializable;

public class IncomeCategory implements Serializable{

    private String category;

    public IncomeCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
