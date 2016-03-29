package max93n.beans;

import max93n.entities.Account;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@ApplicationScoped
public class CurrencyBean {

    public String getCurrencySymbol(Account account) {

        String accountCurrency = account.getCurrency().name();

        switch (accountCurrency) {
            case "USD" :
                return "$";
            case "EUR":
                return "\u20AC";
            case "MDL":
                return "MDL";
        }
        return "";
    }
}
