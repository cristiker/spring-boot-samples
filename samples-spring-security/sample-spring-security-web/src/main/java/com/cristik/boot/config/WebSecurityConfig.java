package com.cristik.boot.config;

import com.cristik.boot.framework.application.service.IUserService;
import com.cristik.boot.framework.security.core.userdetails.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author cristik
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUserService userService;

    @Bean
    protected UserDetailsService userService() {
        UserDetailServiceImpl userDetailsService = new UserDetailServiceImpl();
        userDetailsService.setUserService(userService);
        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }


}

@Configuration
class SecurityConfig implements WebSecurityConfigurer {

    @Override
    public void init(SecurityBuilder builder) throws Exception {
        System.out.println(111);
    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {
        System.out.println(222);
    }
}