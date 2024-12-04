package com.itbank.worldcup.controller;

import com.itbank.worldcup.model.User;
import com.itbank.worldcup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public HashMap<String, Object> handle(IllegalArgumentException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage()); // 예외 메시지를 클라이언트에 전달
        return response;
    }
}
