package com.cylinder.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Ryan Piper
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    /**
     * bcrypt encoder for password logic.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * A general database connection.
     */
    @Autowired
    private DataSource databaseConnection;

    /**
     * Get the user data to authenicate against.
     */
    private static final String userQuery = "SELECT email as username, password, is_enabled as enabled FROM crmuser.accounts where email=?";

    /**
     * Get the secuirty roles a user is associated with.
     */
    private static final String roleQuery = "SELECT accounts.email as username, roles.role FROM crmuser.accounts accounts LEFT JOIN crmuser.roles roles ON accounts.role_id = roles.role_id where accounts.email=?";

    /**
     * Allow authentication to happen with a database.
     *
     * @param authBuilder a builder object that allows one to configure how
     *                    authentication requests are handled.
     */
    @Autowired
    protected void configGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.jdbcAuthentication()
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery)
                .dataSource(databaseConnection)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Secure URIs and set up authorization/authentication logic.
     *
     * @param http allows to map URIs to authorization/authentication logic.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**", "/css/**", "/js/**", "/webjars/**")
                .permitAll()
                .antMatchers("/admin/**", "/h2-console/**")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/lead")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll();
    }

}
