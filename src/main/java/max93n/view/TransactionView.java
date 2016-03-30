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


    @ManagedProperty("#{incomeTransactionService}")
    private IncomeTransactionService incomeTransactionService;

    @ManagedProperty("#{expenseTransactionService}")
    private ExpenseTransactionService expenseTransactionService;


    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{incomeCategoryService}")
    private IncomeCategoryService incomeCategoryService;


    @ManagedProperty("#{expenseCategoryService}")
    private ExpenseCategoryService expenseCategoryService;

    @ManagedProperty("#{expenseSubCategoryService}")
    private ExpenseSubCategoryService expenseSubCategoryService;


    @ManagedProperty("#{tagService}")
    private TagService tagService;

    @ManagedProperty("#{expenseTagService}")
    private ExpenseTagService expenseTagService;


    private Date date;
    private Double amount;
    private String payer;
    private String category;
    private String subCategory;
    private String paymentMethod;
    private String description;

    private List<SelectItem> incomeCategorySelectItems;
    private List<SelectItem> expenseCategorySelectItems;

    private List<Tag> availableTags;
    private List<String> selectedTagTitles;

    private User user;

    private String transactionType;

    @PostConstruct
    public void init() {
        date = new Date();
        amount = 0.0;
        paymentMethod = "Cash";

        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        availableTags = tagService.getAllByUser(user);

        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Map<String, String[]> map = req.getParameterMap();
        transactionType = map.get("type")[0];


        if (transactionType.equals("income")) {
            List<IncomeCategory> incomeCategories= incomeCategoryService.getAllEscapeInitial();

            incomeCategorySelectItems = new ArrayList<>();

            for (IncomeCategory incomeCategory : incomeCategories) {
                incomeCategorySelectItems.add(new SelectItem(incomeCategory.getCategory(), incomeCategory.getCategory()));
            }
        }
        else if (transactionType.equals("expense")) {
            expenseCategorySelectItems = new ArrayList<>();
            List<ExpenseCategory> expenseCategories = expenseCategoryService.getAll();
            for (ExpenseCategory expenseCategory : expenseCategories) {
                SelectItemGroup group = new SelectItemGroup(expenseCategory.getCategory());

                SelectItem [] items = new SelectItem[expenseCategory.getSubCategory().size()];
                int i = 0;
                for (ExpenseSubCategory expenseSubCategory : expenseCategory.getSubCategory()) {
                    items[i++] = new SelectItem(expenseSubCategory.getSubCategory(), expenseSubCategory.getSubCategory());
                }
                group.setSelectItems(items);
                expenseCategorySelectItems.add(group);
            }
        }

    }


    //TODO: add tags, quantity, unit for measure

    public  String addTransaction() {

        String type = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");
        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        if (type.equals("income")) {
            return addIncomeTransaction(accountName);
        }
        else if (type.equals("expense")) {
            return addExpenseTransaction(accountName);
        }

        return null;
    }

    private String addIncomeTransaction(String accountName) {
        IncomeTransaction incomeTransaction = new IncomeTransaction();
        incomeTransaction.setDate(date);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setPayer(payer);
        IncomeCategory incomeCategory = incomeCategoryService.getByCategory(category);
        incomeTransaction.setIncomeCategory(incomeCategory);
        incomeTransaction.setPaymentMethod(paymentMethod);
        incomeTransaction.setDescription(description);
        Account account = accountService.getByName(accountName);
        incomeTransaction.setAccount(account);
        incomeTransactionService.add(incomeTransaction);
        return "dashboard";
    }


    private String addExpenseTransaction(String accountName) {
        ExpenseTransaction expenseTransaction = new ExpenseTransaction();
        expenseTransaction.setDate(date);
        expenseTransaction.setAmount(amount);
        expenseTransaction.setPayer(payer);

        ExpenseSubCategory expenseSubCategory = expenseSubCategoryService.getBySubCategory(subCategory);
        ExpenseCategory expenseCategory = expenseSubCategory.getExpenseCategory();
        expenseTransaction.setExpenseCategory(expenseCategory);

        expenseTransaction.setPaymentMethod(paymentMethod);
        expenseTransaction.setDescription(description);
        Account account = accountService.getByName(accountName);
        expenseTransaction.setAccount(account);

        List<ExpenseTag> expenseTags = new ArrayList<>();

        for (String tagTitle : selectedTagTitles) {
            Tag tag = tagService.getByName(tagTitle);
            ExpenseTag expenseTag = new ExpenseTag();
            expenseTag.setTag(tag);
            expenseTag.setExpenseTransaction(expenseTransaction);
            expenseTags.add(expenseTag);
        }

        expenseTransaction.setExpenseTags(expenseTags);

        expenseTransactionService.add(expenseTransaction);

        for (ExpenseTag expenseTag : expenseTags) {
            expenseTagService.add(expenseTag);
        }

        return "dashboard?faces-redirect=true";
    }


    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public IncomeTransactionService getIncomeTransactionService() {
        return incomeTransactionService;
    }

    public void setIncomeTransactionService(IncomeTransactionService incomeTransactionService) {
        this.incomeTransactionService = incomeTransactionService;
    }

    public IncomeCategoryService getIncomeCategoryService() {
        return incomeCategoryService;
    }

    public void setIncomeCategoryService(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public ExpenseCategoryService getExpenseCategoryService() {
        return expenseCategoryService;
    }

    public void setExpenseCategoryService(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    public List<SelectItem> getExpenseCategorySelectItems() {
        return expenseCategorySelectItems;
    }

    public void setExpenseCategorySelectItems(List<SelectItem> expenseCategorySelectItems) {
        this.expenseCategorySelectItems = expenseCategorySelectItems;
    }


    public ExpenseSubCategoryService getExpenseSubCategoryService() {
        return expenseSubCategoryService;
    }

    public void setExpenseSubCategoryService(ExpenseSubCategoryService expenseSubCategoryService) {
        this.expenseSubCategoryService = expenseSubCategoryService;
    }

    public ExpenseTransactionService getExpenseTransactionService() {
        return expenseTransactionService;
    }

    public void setExpenseTransactionService(ExpenseTransactionService expenseTransactionService) {
        this.expenseTransactionService = expenseTransactionService;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExpenseTagService getExpenseTagService() {
        return expenseTagService;
    }

    public void setExpenseTagService(ExpenseTagService expenseTagService) {
        this.expenseTagService = expenseTagService;
    }
}
