package max93n.view;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import max93n.chart.c3.ChartC3ModelJson;
import max93n.chart.c3.model.Data;
import max93n.entities.Account;
import max93n.entities.ExpenseCategory;
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

    // title, jsonString
    private Map<String, String> tabs;
    private List<String> tabKeys;


    @ManagedProperty("#{accountService}")
    private AccountService accountService;

    @ManagedProperty("#{expenseTransactionService}")
    private ExpenseTransactionService expenseTransactionService;

    @ManagedProperty("#{expenseCategoryService}")
    private ExpenseCategoryService expenseCategoryService;


    @ManagedProperty("#{horizontalBarChartJson}")
    private ChartC3ModelJson horizontalBarChartJson;

    private Account account;

    @PostConstruct
    public void init() {
        filter = "All";

        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        account = accountService.getByName(accountName);

        generateTabForAllPeriod();
    }

    public void filterChanged() {
        if (filter.equals("All")) {
            generateTabForAllPeriod();
        }
        else if (filter.equals("Weekly")) {
            generateTabs(expenseTransactionService.getByWeeks(account));
        }
        else if (filter.equals("Monthly")) {
            generateTabs(expenseTransactionService.getByMonths(account));
        }
        else if (filter.equals("Yearly")) {
            generateTabs(expenseTransactionService.getByYears(account));
        }
    }

    public void generateTabForAllPeriod() {
        generateTabs(expenseTransactionService.getByAllPeriod(account));
    }

    private void generateTabs(List<Object[]> list) {
        tabs = new LinkedHashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // generation of map
        // key: week number, value - array of objects: min date, max date, sum, expenseCategory
        Map<String, List<Object[]>> map = new LinkedHashMap<>();
        for (Object[] objArr : list) {
            String dateStr = Integer.toString((Integer) objArr[0]);

            if (map.containsKey(dateStr)) {
                List<Object[]> tempList = map.get(dateStr);
                tempList.add(new Object[]{
                        objArr[1], objArr[2], objArr[3], objArr[4]
                });
            } else {
                List<Object[]> tempList = new ArrayList<>();
                tempList.add(new Object[]{
                        objArr[1], objArr[2], objArr[3], objArr[4]
                });
                map.put(dateStr, tempList);
            }
        }

        final String[] s = new String[]{};



        // key: week number, value - array of objects: min date, max date, sum, expenseCategory
        for (Map.Entry<String, List<Object[]>> entry : map.entrySet()) {

            Data data = horizontalBarChartJson.getData();

            List<String> headersList = new ArrayList<>();
            headersList.add("headers");
            List<String> expenseList = new ArrayList<>();
            expenseList.add("expense");

            // setting tab-title
            Iterator<Object[]> iter = entry.getValue().iterator();
            Object[] arr = iter.next();
            Date minDate = (Date) arr[0];
            Date maxDate = (Date) arr[1];
            while (iter.hasNext()) {
                arr = iter.next();

                Date nextMinDate = (Date) arr[0];
                Date nextMaxDate = (Date) arr[1];

                if (minDate.getTime() > nextMinDate.getTime()) {
                    minDate.setTime(nextMinDate.getTime());
                }

                if (maxDate.getTime() < nextMaxDate.getTime()) {
                    maxDate.setTime(nextMaxDate.getTime());
                }
            }
            String title = format.format(minDate) + " - " + format.format(maxDate);

            // generation
            iter = entry.getValue().iterator();
            while (iter.hasNext()) {
                arr = iter.next();
                headersList.add(((ExpenseCategory)arr[3]).getCategory());
                expenseList.add(String.valueOf((double)arr[2]));
            }

            data.setColumns(new String[][]{
                    headersList.toArray(s),
                    expenseList.toArray(s)
            });

            ObjectMapper mapper = new ObjectMapper();
            try {
                horizontalBarChartJson.setBindto("#id" + title.replace(" ", ""));
                tabs.put(title, mapper.writeValueAsString(horizontalBarChartJson));
                tabKeys = new ArrayList<>(tabs.keySet());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    public Map<String, String> getTabs() {
        return tabs;
    }

    public void setTabs(Map<String, String> tabs) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<String> getTabKeys() {
        return tabKeys;
    }

    public void setTabKeys(List<String> tabKeys) {
        this.tabKeys = tabKeys;
    }
}

