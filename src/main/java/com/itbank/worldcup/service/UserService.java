package com.itbank.worldcup.service;

import com.itbank.worldcup.mapper.UserMapper;
import com.itbank.worldcup.model.User;
import com.itbank.worldcup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public int joinUser(String username, String password) {
        if (username != null && password != null) {
            //비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(encodedPassword);
            // 유효성 검사 통과 후 DB 저장 등의 로직
            userRepository.save(user);
            return 1; // 저장 성공 시 1 반환
        }
        return 0; // 유효성 검사 실패 시 0 반환
    }

    public boolean isUsernameDuplicated(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    public List<User> getApproveList() {
        return userMapper.getUnapprovedUsers();
    }
}
