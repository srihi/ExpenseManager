package max93n.view;


import max93n.entities.*;
import max93n.enums.TransactionType;
import max93n.services.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ManagedBean
@RequestScoped
public class TransactionView {



    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{categoryService}")
    private CategoryService categoryService;

    @ManagedProperty("#{transactionService}")
    private TransactionService transactionService;

    @ManagedProperty("#{tagService}")
    private TagService tagService;


    private Date date;
    private Double amount;
    private String payer;
    private String subCategory;
    private String paymentMethod;
    private String description;

    List<Tag> availableTags;
    private List<String> selectedTagTitles;

    private List<SelectItem> incomeSubCategorySelectItems;
    private List<SelectItem> expenseCategorySelectItems;

    private double quantity;
    private String selectedMeasure;


    private User user;

    private String transactionType;

    @PostConstruct
    public void init() {
        date = new Date();
        amount = 0.0;
        paymentMethod = "Cash";
        selectedMeasure = "KG";

        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Map<String, String[]> map = req.getParameterMap();
        transactionType = map.get("type")[0];

        availableTags = tagService.getAllByUser(user);


        if (transactionType.equals("income")) {
            List<Category> incomeSubCategories= categoryService.getIncomeSubCategoriesEscapeInitial();

            incomeSubCategorySelectItems = new ArrayList<>();

            for (Category incomeSubCategory : incomeSubCategories) {
                incomeSubCategorySelectItems.add(new SelectItem(incomeSubCategory.getName(), incomeSubCategory.getName()));
            }
        }
        else if (transactionType.equals("expense")) {

            expenseCategorySelectItems = new ArrayList<>();

            List<Category> expenseCategories = categoryService.getCategories();
            for (Category expenseCategory : expenseCategories) {

                SelectItemGroup group = new SelectItemGroup(expenseCategory.getName());
                Set<Category> expenseSubCategory = expenseCategory.getChildren();

                List<SelectItem> items = new ArrayList<>();
                for (Category subCategory : expenseSubCategory) {
                    items.add(new SelectItem(subCategory.getName(), subCategory.getName()));
                }
                SelectItem[] itemsArr= new SelectItem[items.size()];
                group.setSelectItems(items.toArray(itemsArr));

                expenseCategorySelectItems.add(group);
            }
        }

    }

    public  String addTransaction() {

        String type = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");
        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        Transaction expenseTransaction = new Transaction();

        expenseTransaction.setDate(date);
        expenseTransaction.setAmount(amount);
        expenseTransaction.setPayer(payer);

        expenseTransaction.setPaymentMethod(paymentMethod);
        expenseTransaction.setDescription(description);
        Account account = accountService.getByName(accountName);
        expenseTransaction.setAccount(account);


        if (type.equals("income")) {
            expenseTransaction.setTransactionType(TransactionType.INCOME);
        }
        else if (type.equals("expense")) {
            expenseTransaction.setTransactionType(TransactionType.EXPENSE);
        }

        Category category = categoryService.getByCategoryName(subCategory);
        expenseTransaction.setCategory(category);


        List<Tag> expenseTags = new ArrayList<>();

        for (String tagTitle : selectedTagTitles) {
            Tag tag = tagService.getByName(tagTitle);
            expenseTags.add(tag);
        }

        expenseTransaction.setTags(expenseTags);
        expenseTransaction.setQuantity(quantity);
        expenseTransaction.setMeasure(selectedMeasure);

        transactionService.add(expenseTransaction);

        return "dashboard?faces-redirect=true";

    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }


    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SelectItem> getIncomeSubCategorySelectItems() {
        return incomeSubCategorySelectItems;
    }

    public void setIncomeSubCategorySelectItems(List<SelectItem> incomeSubCategorySelectItems) {
        this.incomeSubCategorySelectItems = incomeSubCategorySelectItems;
    }

    public List<SelectItem> getExpenseCategorySelectItems() {
        return expenseCategorySelectItems;
    }

    public void setExpenseCategorySelectItems(List<SelectItem> expenseCategorySelectItems) {
        this.expenseCategorySelectItems = expenseCategorySelectItems;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSelectedMeasure() {
        return selectedMeasure;
    }

    public void setSelectedMeasure(String selectedMeasure) {
        this.selectedMeasure = selectedMeasure;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public List<Tag> getAvailableTags() {
        return availableTags;
    }

    public void setAvailableTags(List<Tag> availableTags) {
        this.availableTags = availableTags;
    }

    public List<String> getSelectedTagTitles() {
        return selectedTagTitles;
    }

    public void setSelectedTagTitles(List<String> selectedTagTitles) {
        this.selectedTagTitles = selectedTagTitles;
    }
}