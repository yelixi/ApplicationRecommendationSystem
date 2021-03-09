package com.example.demo.model;

import com.example.demo.entity.User;
import com.example.demo.enums.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/3/9 13:57
 */
public class UserInformation extends User implements UserDetails {

    public UserInformation(User user) {
        BeanUtils.copyProperties(user, this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //添加用户权限
        authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.values()[Integer.parseInt(getRole())].toString()));
        return authorities;
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
        return getId() != null;
    }
}
