package com.itbank.worldcup.service;

import com.itbank.worldcup.model.User;
import com.itbank.worldcup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        log.info(String.valueOf(user));

        // 승인되지 않은 사용자 체크
        if (!user.isApproved()) {
            throw new UsernameNotFoundException("사용자가 아직 승인되지 않았습니다.");
        }
        // Spring Security UserDetails 객체 반환
        return new CustomUserDetails(user);
    }
}
