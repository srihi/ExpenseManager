package max93n.beans;


import max93n.entities.User;
import max93n.services.UserService;
import max93n.utils.PasswordHelper;
import max93n.utils.exceptions.UserExistsException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.constraints.Size;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class AuthBean {


    @ManagedProperty("#{userService}")
    private UserService userService;

    @ManagedProperty("#{userDetailsService}")
    private UserDetailsService userDetailsService;

    @ManagedProperty("#{passwordHelper}")
    private PasswordHelper passwordHelper;

    @NotEmpty(message = "Not empty")
    @Size(min = 3, message = "Minimum size is 3")
    private String username;

    @Size(min = 3, message = "Minimum size is 3")
    private String password1;

    @Size(min = 3, message = "Minimum size is 3")
    private String password2;

    // validation will be with FacesValidator
    private String email;


    public AuthBean() {
    }


    public String doLogin() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();

        return null;
    }


    public String registrate() {

        if (!password1.equals(password2)) {
            return "registration";
        }

        try {
            userService.registerNewUserAccount(username, password1, email);
        } catch (UserExistsException e) {
            return "registration";
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "dashboard?faces-redirect=true";
        }

        return "registration";
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public PasswordHelper getPasswordHelper() {
        return passwordHelper;
    }

    public void setPasswordHelper(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }
}

