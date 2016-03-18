package max93n.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validators.balanceValidator")
public class BalanceValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        try {

            if (o == null) {
                throw new IllegalArgumentException("Enter balance");
            }

            double balance = Double.parseDouble(o.toString());

            if (balance < 0) {
                throw new IllegalArgumentException("It must be positive");
            }


        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("It must be a number");
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
}
