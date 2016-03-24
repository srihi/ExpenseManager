package max93n.view;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import max93n.chart.c3.ChartC3ModelJson;
import max93n.chart.c3.model.Data;
import max93n.entities.Account;
import max93n.entities.ExpenseCategory;
import max93n.entities.ExpenseTransaction;
import max93n.services.AccountService;
import max93n.services.ExpenseCategoryService;
import max93n.services.ExpenseTransactionService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@ViewScoped
public class ChartsView {

    private String filter;

    private String jsonString;

    private List<String> tabs;

    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{expenseTransactionService}")
    private ExpenseTransactionService expenseTransactionService;

    @ManagedProperty("#{expenseCategoryService}")
    private ExpenseCategoryService expenseCategoryService;


    @ManagedProperty("#{horizontalBarChartJson}")
    private ChartC3ModelJson horizontalBarChartJson;

    @PostConstruct
    public void init() {
        filter = "All";

        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        Account account = accountService.getByName(accountName);

        Date firstDate = expenseTransactionService.getFirstDateOfExpense(account);
        Date lastDate = expenseTransactionService.getLastDateOfExpense(account);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        tabs = new ArrayList<>();
        tabs.add(format.format(firstDate) + " - " + format.format(lastDate));


        Data data = horizontalBarChartJson.getData();
        List<ExpenseCategory> expenseCategories = expenseCategoryService.getAll();


        List<String> headersList = new ArrayList<>();
        headersList.add("headers");
        List<String> expenseList = new ArrayList<>();
        expenseList.add("expense");
        final String[] s = new String[]{};
        for (ExpenseCategory expenseCategory : expenseCategories) {

            Double sum = expenseTransactionService.getSumFormCategory(account, expenseCategory);
            if (sum != null) {
                headersList.add(expenseCategory.getCategory());
                expenseList.add(String.valueOf(sum.doubleValue()));
            }

        }

        data.setColumns(new String[][]{
                headersList.toArray(s),
                expenseList.toArray(s)

        });


        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(horizontalBarChartJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void filterChanged() {
        System.out.println(filter);
    }

    public List<String> getTabs() {
        return tabs;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }


    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public ChartC3ModelJson getHorizontalBarChartJson() {
        return horizontalBarChartJson;
    }

    public void setHorizontalBarChartJson(ChartC3ModelJson horizontalBarChartJson) {
        this.horizontalBarChartJson = horizontalBarChartJson;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }


    public ExpenseTransactionService getExpenseTransactionService() {
        return expenseTransactionService;
    }

    public void setExpenseTransactionService(ExpenseTransactionService expenseTransactionService) {
        this.expenseTransactionService = expenseTransactionService;
    }

    public ExpenseCategoryService getExpenseCategoryService() {
        return expenseCategoryService;
    }

    public void setExpenseCategoryService(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }



}

