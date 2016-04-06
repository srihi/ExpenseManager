package max93n.view;


import com.google.gson.Gson;
import max93n.chart.c3.ChartC3ModelJson;
import max93n.chart.c3.model.Data;
import max93n.entities.Account;
import max93n.entities.Category;
import max93n.enums.TransactionType;
import max93n.services.AccountService;
import max93n.services.TransactionService;

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


    @ManagedProperty("#{accountService}")
    private AccountService accountService;


    @ManagedProperty("#{transactionService}")
    private TransactionService transactionService;


    @ManagedProperty("#{verticalBarChartJson}")
    private ChartC3ModelJson verticalBarChartJson;

    @ManagedProperty("#{horizontalBarChartJson}")
    private ChartC3ModelJson horizontalBarChartJson;


    private int chartType;

    private String filter;

    private String jsonString;

    // title, jsonString
    private Map<String, String> tabs;
    private List<String> tabKeys;

    private Account account;

    private boolean filterNeed;


    @PostConstruct
    public void init() {
        filter = "All";

        String accountName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        account = accountService.getByName(accountName);

        generateTabForAllPeriod();
        filterNeed = true;
        chartType = 1;
    }

    public void chartTypeChanged() {
        if (chartType == 1) {
            filterNeed = true;
            filterChanged();
        } else {
            generateTabForIncomeExpense();
            filterNeed = false;
        }
    }


    public void filterChanged() {
        if (filter.equals("All")) {
            generateTabForAllPeriod();
        } else if (filter.equals("Weekly")) {
            generateTabs(transactionService.getByWeeks(account));
        } else if (filter.equals("Monthly")) {
            generateTabs(transactionService.getByMonths(account));
        } else if (filter.equals("Yearly")) {
            generateTabs(transactionService.getByYears(account));
        }
    }

    /**
     * Generate map
     *
     * @param map  target map
     * @param list contain: month, year, sum. Grouped by month
     * @param pos  index in Double[]. 0 - expense, 1 - income
     */
    private void generateMap(Map<Integer, Map<Integer, Double[]>> map, List<Object[]> list, int pos) {
        for (Object[] objArr : list) {
            Integer yearKey = (Integer) objArr[1];
            Integer monthKey = (Integer) objArr[0];
            if (map.containsKey(yearKey)) {
                Map<Integer, Double[]> subMap = map.get(yearKey);
                Double[] sumArray = new Double[2];
                if (subMap.containsKey(monthKey)) {
                    subMap.get(monthKey)[pos] = (Double) objArr[2];

                } else {
                    sumArray[pos] = (Double) objArr[2];
                    subMap.put(monthKey, sumArray);
                }

            } else {
                Map<Integer, Double[]> subMap = new TreeMap<>();
                Double[] sumArray = new Double[2];
                sumArray[pos] = (Double) objArr[2];

                subMap.put(monthKey, sumArray);
                map.put(yearKey, subMap);
            }
        }
    }

    public void generateTabForIncomeExpense() {

        List<String> headersList = new ArrayList<>();
        headersList.add("months");
        List<String> expenseList = new ArrayList<>();
        expenseList.add("expense");
        List<String> incomeList = new ArrayList<>();
        incomeList.add("income");

        tabs = new LinkedHashMap<>();
        String title = "Monthly incomes expenses";


        List<Object[]> expenseTransactions = transactionService.getSumGroupedByMonthsOfYear(account, TransactionType.EXPENSE);
        List<Object[]> incomeTransactions = transactionService.getSumGroupedByMonthsOfYear(account, TransactionType.INCOME);

        Map<Integer, Map<Integer, Double[]>> map = new TreeMap<>();
        generateMap(map, expenseTransactions, 0);
        generateMap(map, incomeTransactions, 1);


        Data data = horizontalBarChartJson.getData();

        for (Map.Entry<Integer, Map<Integer, Double[]>> entry : map.entrySet()) {

            for (Map.Entry<Integer, Double[]> subEntry : entry.getValue().entrySet()) {
                headersList.add(entry.getKey() + " " + subEntry.getKey());

                if (subEntry.getValue()[0] == null) {
                    expenseList.add("0.0");
                } else {
                    expenseList.add(String.valueOf(subEntry.getValue()[0]));
                }

                if (subEntry.getValue()[1] == null) {
                    incomeList.add("0.0");
                } else {
                    incomeList.add(String.valueOf(subEntry.getValue()[1]));
                }
            }
        }

        final String[] s = new String[]{};
        data.setColumns(new String[][]{
                headersList.toArray(s),
                expenseList.toArray(s),
                incomeList.toArray(s)
        });

        horizontalBarChartJson.setData(data);

        Gson gson = new Gson();
        horizontalBarChartJson.setBindto("#id" + title.replace(" ", ""));
        tabs.put(title, gson.toJson(horizontalBarChartJson));
        tabKeys = new ArrayList<>(tabs.keySet());

    }


    public void generateTabForAllPeriod() {
        generateTabs(transactionService.getByAllPeriod(account, TransactionType.EXPENSE));
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

            Data data = verticalBarChartJson.getData();

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
                headersList.add(((Category) arr[3]).getName());
                expenseList.add(String.valueOf((double) arr[2]));
            }

            data.setColumns(new String[][]{
                    headersList.toArray(s),
                    expenseList.toArray(s)
            });

            Gson gson = new Gson();
            verticalBarChartJson.setBindto("#id" + title.replace(" ", ""));
            tabs.put(title, gson.toJson(verticalBarChartJson));
            tabKeys = new ArrayList<>(tabs.keySet());
        }
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public ChartC3ModelJson getVerticalBarChartJson() {
        return verticalBarChartJson;
    }

    public void setVerticalBarChartJson(ChartC3ModelJson verticalBarChartJson) {
        this.verticalBarChartJson = verticalBarChartJson;
    }

    public ChartC3ModelJson getHorizontalBarChartJson() {
        return horizontalBarChartJson;
    }

    public void setHorizontalBarChartJson(ChartC3ModelJson horizontalBarChartJson) {
        this.horizontalBarChartJson = horizontalBarChartJson;
    }

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
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

    public Map<String, String> getTabs() {
        return tabs;
    }

    public void setTabs(Map<String, String> tabs) {
        this.tabs = tabs;
    }

    public List<String> getTabKeys() {
        return tabKeys;
    }

    public void setTabKeys(List<String> tabKeys) {
        this.tabKeys = tabKeys;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isFilterNeed() {
        return filterNeed;
    }

    public void setFilterNeed(boolean filterNeed) {
        this.filterNeed = filterNeed;
    }
}