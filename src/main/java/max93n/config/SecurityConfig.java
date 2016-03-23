package max93n.config;

import max93n.services.impl.CustomUserDetailServiceImpl;
import max93n.utils.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordHelper);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // включаем защиту от CSRF атак
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll();


        http.authorizeRequests()
                .antMatchers("/registration.jsf").access("isAnonymous()")
                .antMatchers("/dashboard.jsf").access("isAuthenticated()")
                .antMatchers("/transaction.jsf").access("isAuthenticated()")
                .antMatchers("/incomeCategory.jsf").access("isAuthenticated()")
                .antMatchers("/expenseCategory.jsf").access("isAuthenticated()");


        http.formLogin()
                .loginPage("/login.jsf")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login.jsf?status=error")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/dashboard.jsf");

        http.logout()
                .permitAll()
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/login.jsf")
                .invalidateHttpSession(true);
    }
}

