package com.example.itss.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Giả sử bạn có User trong cơ sở dữ liệu
        if ("hoang".equals(username)) {
            return new User("hoang", "{noop}password", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User không tồn tại: " + username);
        }
    }
}
