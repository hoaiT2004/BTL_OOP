package com.example.btl_oop.config;

import com.example.btl_oop.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    public MyUserDetails(User user) {
        username = user.getUsername();
        password = user.getPassword();
        authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole_id().getRole_name().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
