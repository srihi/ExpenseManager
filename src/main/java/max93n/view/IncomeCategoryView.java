package max93n.view;


import max93n.models.account.IncomeCategory;
import max93n.services.IncomeCategoryService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class IncomeCategoryView {

    @ManagedProperty("#{incomeCategoryService}")
    private IncomeCategoryService incomeCategoryService;

    private List<IncomeCategory> incomeCategories;

    private String category;

    @PostConstruct
    public void init() {
        incomeCategories = incomeCategoryService.getAll();
    }


    public void addCategory() {
        IncomeCategory incomeCategory = incomeCategoryService.getByCategory(category);
        if (incomeCategory != null) {
            return;
        }

        incomeCategory = new IncomeCategory();
        incomeCategory.setCategory(category);
        incomeCategoryService.add(incomeCategory);
        incomeCategories.add(incomeCategory);
    }

    public void removeCategory(IncomeCategory category) {
        incomeCategoryService.remove(category);
        incomeCategories.remove(category);
    }


    public IncomeCategoryService getIncomeCategoryService() {
        return incomeCategoryService;
    }

    public void setIncomeCategoryService(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    public List<IncomeCategory> getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(List<IncomeCategory> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
