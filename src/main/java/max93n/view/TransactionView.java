package max93n.view;


import max93n.entities.*;
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



    private Date date;
    private Double amount;
    private String payer;
    private String category;
    private String subCategory;
    private String paymentMethod;
    private String description;

    private List<SelectItem> incomeCategorySelectItems;
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


        if (transactionType.equals("income")) {
//            List<IncomeCategory> incomeCategories= incomeCategoryService.getAllEscapeInitial();
//
//            incomeCategorySelectItems = new ArrayList<>();
//
//            for (IncomeCategory incomeCategory : incomeCategories) {
//                incomeCategorySelectItems.add(new SelectItem(incomeCategory.getCategory(), incomeCategory.getCategory()));
//            }
        }
        else if (transactionType.equals("expense")) {
//            expenseCategorySelectItems = new ArrayList<>();
//
//            List<ExpenseCategory> expenseCategories = expenseCategoryService.getAll();
//            for (ExpenseCategory expenseCategory : expenseCategories) {
//                SelectItemGroup group = new SelectItemGroup(expenseCategory.getCategory());
//
//                SelectItem [] items = new SelectItem[expenseCategory.getSubCategory().size()];
//                int i = 0;
//                for (ExpenseSubCategory expenseSubCategory : expenseCategory.getSubCategory()) {
//                    items[i++] = new SelectItem(expenseSubCategory.getSubCategory(), expenseSubCategory.getSubCategory());
//                }
//                group.setSelectItems(items);
//
//                expenseCategorySelectItems.add(group);
//            }

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

        if (type.equals("income")) {
//            return addIncomeTransaction(accountName);
        }
        else if (type.equals("expense")) {
            return addExpenseTransaction(accountName);
        }

        return null;
    }

//    private String addIncomeTransaction(String accountName) {
//        IncomeTransaction incomeTransaction = new IncomeTransaction();
//        incomeTransaction.setDate(date);
//        incomeTransaction.setAmount(amount);
//        incomeTransaction.setPayer(payer);
//        IncomeCategory incomeCategory = incomeCategoryService.getByCategory(category);
//        incomeTransaction.setIncomeCategory(incomeCategory);
//        incomeTransaction.setPaymentMethod(paymentMethod);
//        incomeTransaction.setDescription(description);
//        Account account = accountService.getByName(accountName);
//        incomeTransaction.setAccount(account);
//
//        List<IncomeTag> incomeTags = new ArrayList<>();
//
//        for (String tagTitle : selectedTagTitles) {
//            Tag tag = tagService.getByName(tagTitle);
//            IncomeTag incomeTag = new IncomeTag();
////            incomeTag.setIncomeTag(tag);
////            incomeTag.setIncomeTransaction(incomeTransaction);
////            incomeTags.add(incomeTag);
//        }
//
////        incomeTransaction.setIncomeTags(incomeTags);
//        incomeTransaction.setQuantity(quantity);
//        incomeTransaction.setMeasure(selectedMeasure);
//
//        incomeTransactionService.add(incomeTransaction);
//
//        for (IncomeTag incomeTag : incomeTags) {
//            incomeTagService.add(incomeTag);
//        }
//
//        return "dashboard";
//    }


    private String addExpenseTransaction(String accountName) {
        Transaction expenseTransaction = new Transaction();
        expenseTransaction.setDate(date);
        expenseTransaction.setAmount(amount);
        expenseTransaction.setPayer(payer);


        Category category = categoryService.getByCategoryName(subCategory);

        expenseTransaction.setCategory(category);

        expenseTransaction.setPaymentMethod(paymentMethod);
        expenseTransaction.setDescription(description);
        Account account = accountService.getByName(accountName);
        expenseTransaction.setAccount(account);

//        List<ExpenseTag> expenseTags = new ArrayList<>();

//        for (String tagTitle : selectedTagTitles) {
//            Tag tag = tagService.getByName(tagTitle);
//            ExpenseTag expenseTag = new ExpenseTag();
//            expenseTag.setExpenseTag(tag);
//            expenseTag.setExpenseTransaction(expenseTransaction);
//            expenseTags.add(expenseTag);
//        }

//        expenseTransaction.setExpenseTags(expenseTags);
        expenseTransaction.setQuantity(quantity);
        expenseTransaction.setMeasure(selectedMeasure);

        transactionService.add(expenseTransaction);

//        for (ExpenseTag expenseTag : expenseTags) {
//            expenseTagService.add(expenseTag);
//        }

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

    public List<SelectItem> getIncomeCategorySelectItems() {
        return incomeCategorySelectItems;
    }

    public void setIncomeCategorySelectItems(List<SelectItem> incomeCategorySelectItems) {
        this.incomeCategorySelectItems = incomeCategorySelectItems;
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
}