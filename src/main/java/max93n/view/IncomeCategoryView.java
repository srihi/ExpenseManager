package max93n.view;


import max93n.entities.Category;
import max93n.services.CategoryService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class IncomeCategoryView {

    @ManagedProperty("#{categoryService}")
    private CategoryService categoryService;

    private List<Category> incomeSubCategories;

    private Category modifyingIncomeSubCategory;

    private String name;

    @PostConstruct
    public void init() {
        incomeSubCategories = categoryService.getIncomeSubCategoriesEscapeInitial();

    }

    public void save() {
        if (modifyingIncomeSubCategory != null) {
            categoryService.save(modifyingIncomeSubCategory);
        }
    }

    public void edit(Category incomeSubCategory) {
        modifyingIncomeSubCategory = incomeSubCategory;
    }

    public void addCategory() {
        Category incomeSubCategory = categoryService.getByCategoryName(name);
        if (incomeSubCategory != null) {
            return;
        }

        incomeSubCategory = new Category();
        incomeSubCategory.setName(name);

        Category parent = categoryService.getByCategoryName("Income");
        incomeSubCategory.setParent(parent);
        incomeSubCategory.setTransactions(new ArrayList<>());

        categoryService.add(incomeSubCategory);
        incomeSubCategories.add(incomeSubCategory);
    }

    public void removeCategory(Category category) {
        //parent null, because I can't delete it with parent
        category.setParent(null);
        categoryService.save(category);
        categoryService.remove(category);
        incomeSubCategories.remove(category);
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getIncomeSubCategories() {
        return incomeSubCategories;
    }

    public void setIncomeSubCategories(List<Category> incomeSubCategories) {
        this.incomeSubCategories = incomeSubCategories;
    }

    public Category getModifyingIncomeSubCategory() {
        return modifyingIncomeSubCategory;
    }

    public void setModifyingIncomeSubCategory(Category modifyingIncomeSubCategory) {
        this.modifyingIncomeSubCategory = modifyingIncomeSubCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}