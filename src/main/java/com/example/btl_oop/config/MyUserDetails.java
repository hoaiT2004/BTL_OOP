package com.example.btl_oop.config;

import com.example.btl_oop.entity.Role;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    public MyUserDetails(User user, Role role) {
        username = user.getUsername();
        password = user.getPassword();
        if (role != null && role.getRole_name() != null) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getRole_name().name()));
        }
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
