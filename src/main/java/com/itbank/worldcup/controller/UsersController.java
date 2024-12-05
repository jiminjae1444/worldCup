package com.itbank.worldcup.controller;

import com.itbank.worldcup.model.User;
import com.itbank.worldcup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping("/join")
    public HashMap<String, Object> join(User user) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            int row = userService.joinUser(user);
            response.put("success", row != 0);
        } catch (IllegalArgumentException ex) {
            response.put("error", ex.getMessage());
        }
        return response;
    }

    @PostMapping("/check")
    public HashMap<String, Object> check(String username) {
        HashMap<String, Object> response = new HashMap<>();
        // 아이디 길이 검사
        if (username.length() < 3) {
            response.put("success", false);
            response.put("message", "아이디는 최소 3자 이상이어야 합니다.");
            response.put("color", "red");
            return response;
        }
        boolean isDuplicated = userService.isUsernameDuplicated(username);
        // 중복되지 않으면 색상도 포함하여 반환
        if (isDuplicated) {
            response.put("success", false);  // 중복된 아이디일 경우
            response.put("message", "이미 사용 중인 아이디입니다.");
            response.put("color", "red");  // 오류를 나타내는 빨간색
        } else {
            response.put("success", true);  // 중복되지 않으면
            response.put("message", "사용 가능한 아이디입니다.");
            response.put("color", "green");  // 정상적으로 사용 가능한 아이디는 초록색
        }
        return response;
    }

    @GetMapping("/approveList")
    public HashMap<String, Object> approveList() {
        HashMap<String, Object> response = new HashMap<>();
        // 승인되지 않은 사용자 목록 가져오기
        List<User> approveList = userService.getApproveList();
        // 응답에 데이터 추가
        response.put("approveList", approveList);
        response.put("success", approveList != null);
        response.put("approveListSize", approveList != null);
        return response;
    }
}
