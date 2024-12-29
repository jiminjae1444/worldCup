package com.itbank.worldcup.service;

import com.itbank.worldcup.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomUserDetails implements UserDetails {
    private User user;  // User 객체를 포함

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // UserDetails 메서드 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한을 설정합니다. 예를 들어 'ROLE_USER' 또는 'ROLE_ADMIN'
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        log.info("역할 : " + user.getRole().name());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // ROLE_USER 또는 ROLE_ADMIN
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public User getUser() {
        return user;  // User 객체 반환
    }
}
