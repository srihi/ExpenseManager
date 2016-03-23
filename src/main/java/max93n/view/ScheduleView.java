package max93n.view;


import max93n.entities.Account;
import max93n.entities.AppTransaction;
import max93n.entities.ExpenseTransaction;
import max93n.entities.IncomeTransaction;
import max93n.services.AccountService;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class ScheduleView {


    @ManagedProperty("#{accountService}")
    private AccountService accountService;


    private ScheduleModel eventModel;

    @PostConstruct
    public void init() {

        eventModel = new DefaultScheduleModel();

        String accountName =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("account-name");

        Account account = accountService.getByName(accountName);

        List<ExpenseTransaction> expenseTransactions = account.getExpenseTransactions();
        generateEvents(eventModel, expenseTransactions, "expense-color");

        List<IncomeTransaction> incomeTransactions = account.getIncomeTransactions();
        generateEvents(eventModel, incomeTransactions, "income-color");
    }

    private void generateEvents(ScheduleModel eventModel, List<? extends AppTransaction> transactions,
                                String styleClass) {
        for (AppTransaction transaction : transactions) {

            Date date = transaction.getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR, c.get(Calendar.HOUR) + 1);


            DefaultScheduleEvent event = new DefaultScheduleEvent(String.valueOf(transaction.getAmount()),
                    date,
                    c.getTime(), styleClass);

            eventModel.addEvent(event);
        }


    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }


    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
