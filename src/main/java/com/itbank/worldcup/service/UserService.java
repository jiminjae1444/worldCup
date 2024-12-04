package com.itbank.worldcup.service;

import com.itbank.worldcup.model.User;
import com.itbank.worldcup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public int joinUser(User user) {
        if (user != null) {
            // 1. 아이디 유효성 체크: 최소 3자 이상
            if (user.getUsername().length() < 3) {
                throw new IllegalArgumentException("아이디는 최소 3자 이상이어야 합니다.");
            }

            // 2. 비밀번호 유효성 체크: 최소 8자 이상, 대소문자, 특수문자 포함
            if (!isValidPassword(user.getPassword())) {
                throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 하며, 대소문자 및 특수문자가 포함되어야 합니다.");
            }
            //비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            // 유효성 검사 통과 후 DB 저장 등의 로직
            userRepository.save(user);
            return 1; // 저장 성공 시 1 반환
        }
        return 0; // 유효성 검사 실패 시 0 반환
    }

    private boolean isValidPassword(String password) {
        // 1. 비밀번호 길이가 8자 이상인지 확인
        if (password.length() < 8) {
            return false;
        }
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecialChar = false;

        // 2. 각 조건을 만족하는지 확인
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (isSpecialCharacter(c)) {
                hasSpecialChar = true;
            }
        }
        // 3. 조건을 모두 만족하는지 확인
        return hasLower && hasUpper && hasSpecialChar;
    }

    private boolean isSpecialCharacter(char c) {
        String specialChars = "@#$%^&+=!"; // 필요한 특수문자들
        return specialChars.indexOf(c) >= 0;
    }
}
