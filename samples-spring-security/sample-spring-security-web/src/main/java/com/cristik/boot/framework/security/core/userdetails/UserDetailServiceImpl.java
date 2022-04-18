package com.cristik.sample.framework.security.core.userdetails;

import com.cristik.sample.framework.application.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author cristik
 */
public class UserDetailServiceImpl implements UserDetailsService {

    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
