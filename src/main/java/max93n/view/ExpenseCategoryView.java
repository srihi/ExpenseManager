package max93n.view;

import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseSubCategory;
import max93n.services.ExpenseCategoryService;
import max93n.services.ExpenseSubCategoryService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class ExpenseCategoryView {

    @ManagedProperty("#{expenseCategoryService}")
    private ExpenseCategoryService expenseCategoryService;

    @ManagedProperty("#{expenseSubCategoryService}")
    private ExpenseSubCategoryService expenseSubCategoryService;


    private String category;
    private String subCategory;

    private ExpenseCategory selectedExpenseCategory;
    private ExpenseSubCategory selectedSubExpenseCategory;

    private TreeNode root;

    @PostConstruct
    public void init() {
        List<ExpenseCategory> expenseCategories = expenseCategoryService.getAll();

        root = new DefaultTreeNode("CategoriesRoot", null);

        for (ExpenseCategory expenseCategory : expenseCategories) {
            TreeNode node = new DefaultTreeNode(expenseCategory.getCategory(), root);
            for (ExpenseSubCategory expenseSubCategory : expenseCategory.getSubCategory()) {
                TreeNode subNode = new DefaultTreeNode(expenseSubCategory.getSubCategory(), node);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {

        if (event.getTreeNode().getChildCount() == 0) {
            //selected subCategory
            subCategory = event.getTreeNode().getData().toString();
            category = event.getTreeNode().getParent().getData().toString();

            selectedExpenseCategory = expenseCategoryService.getByCategory(category);
            selectedSubExpenseCategory = expenseSubCategoryService.getBySubCategory(subCategory);
        }
        else {
            //selected category
            category = event.getTreeNode().getData().toString();
            selectedExpenseCategory = new ExpenseCategory();
            subCategory = "";

            selectedExpenseCategory = expenseCategoryService.getByCategory(category);
        }

    }

    public void add() {
        if (category != null && !category.trim().equals("") && subCategory != null && !subCategory.trim().equals("")) {
            expenseCategoryService.add(category, subCategory);
        }
        init();
    }

    public void editCategory() {
        if (category != null && !category.trim().equals("")) {
            selectedExpenseCategory.setCategory(category);
            expenseCategoryService.edit(selectedExpenseCategory);
        }
        init();
    }

    public void editSubCategory() {
        if (category != null && !category.trim().equals("") && subCategory != null && !subCategory.trim().equals("")) {
            //TODO editSubCategory
        }
        init();
    }

    public void removeCategory() {
        if (category != null && !category.trim().equals("")) {
            //TODO removeCategory
            expenseCategoryService.remove(selectedExpenseCategory);
            selectedExpenseCategory = null;
            selectedSubExpenseCategory = null;
        }
        init();
    }

    public void removeSubCategory() {
        if (category != null && !category.trim().equals("") && subCategory != null && !subCategory.trim().equals("")) {
            //TODO removeSubCategory
        }
        init();
    }


    public void reset() {
        category = "";
        subCategory = "";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public ExpenseCategoryService getExpenseCategoryService() {
        return expenseCategoryService;
    }

    public void setExpenseCategoryService(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    public ExpenseCategory getSelectedExpenseCategory() {
        return selectedExpenseCategory;
    }

    public void setSelectedExpenseCategory(ExpenseCategory selectedExpenseCategory) {
        this.selectedExpenseCategory = selectedExpenseCategory;
    }

    public ExpenseSubCategoryService getExpenseSubCategoryService() {
        return expenseSubCategoryService;
    }

    public void setExpenseSubCategoryService(ExpenseSubCategoryService expenseSubCategoryService) {
        this.expenseSubCategoryService = expenseSubCategoryService;
    }
}
