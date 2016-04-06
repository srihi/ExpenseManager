package max93n.view;

import max93n.entities.Category;
import max93n.services.CategoryService;
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

    @ManagedProperty("#{categoryService}")
    private CategoryService categoryService;



    private String categoryName;
    private String subCategoryName;

    private Category selectedExpenseCategory;
    private Category selectedExpenseSubCategory;

    private TreeNode root;

    @PostConstruct
    public void init() {
        List<Category> expenseCategories = categoryService.getExpenseCategories();

        root = new DefaultTreeNode("CategoriesRoot", null);

        for (Category expenseCategory : expenseCategories) {
            TreeNode node = new DefaultTreeNode(expenseCategory.getName(), root);
            for (Category expenseSubCategory : expenseCategory.getChildren()) {
                TreeNode subNode = new DefaultTreeNode(expenseSubCategory.getName(), node);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {

        if (event.getTreeNode().getChildCount() == 0 && !event.getTreeNode().getParent().getData().equals("CategoriesRoot")) {
            //selected subCategory
            subCategoryName = event.getTreeNode().getData().toString();
            categoryName = event.getTreeNode().getParent().getData().toString();

            selectedExpenseCategory = categoryService.getByCategoryName(categoryName);
            selectedExpenseSubCategory = categoryService.getByCategoryName(subCategoryName);
        }
        else {
            //selected category
            categoryName = event.getTreeNode().getData().toString();
            selectedExpenseCategory = new Category();
            subCategoryName = "";

            selectedExpenseCategory = categoryService.getByCategoryName(categoryName);
        }

    }

    public void add() {
        if (categoryName != null && !categoryName.trim().equals("") && subCategoryName != null && !subCategoryName.trim().equals("")) {
            categoryService.add(categoryName, subCategoryName);
            reset();
        }
        init();
    }

    public void editCategory() {
        if (categoryName != null && !categoryName.trim().equals("")) {
            selectedExpenseCategory.setName(categoryName);
            categoryService.edit(selectedExpenseCategory);
            reset();
        }
        init();
    }

    public void editSubCategory() {
        if (categoryName != null && !categoryName.trim().equals("") && subCategoryName != null && !subCategoryName.trim().equals("")) {
            selectedExpenseSubCategory.setName(subCategoryName);
            categoryService.edit(selectedExpenseSubCategory);
            reset();
        }
        init();
    }

    public void removeCategory() {
        if (categoryName != null && !categoryName.trim().equals("")) {
            categoryService.remove(selectedExpenseCategory);
            reset();
        }
        init();
    }
    public void removeSubCategory() {
        if (categoryName != null && !categoryName.trim().equals("") && subCategoryName != null && !subCategoryName.trim().equals("")) {
            categoryService.remove(selectedExpenseSubCategory);
            reset();
        }
        init();
    }


    public void reset() {
        categoryName = null;
        subCategoryName = null;
        selectedExpenseCategory = null;
        selectedExpenseSubCategory = null;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Category getSelectedExpenseCategory() {
        return selectedExpenseCategory;
    }

    public void setSelectedExpenseCategory(Category selectedExpenseCategory) {
        this.selectedExpenseCategory = selectedExpenseCategory;
    }

    public Category getSelectedExpenseSubCategory() {
        return selectedExpenseSubCategory;
    }

    public void setSelectedExpenseSubCategory(Category selectedExpenseSubCategory) {
        this.selectedExpenseSubCategory = selectedExpenseSubCategory;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}