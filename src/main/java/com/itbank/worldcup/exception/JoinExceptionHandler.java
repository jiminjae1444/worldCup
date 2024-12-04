package com.itbank.worldcup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class JoinExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HashMap<String, String>> handleInvalidArgumentException(IllegalArgumentException ex) {
        // 예외 메시지를 포함한 응답 생성
        HashMap<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage()); // 예외 메시지를 클라이언트에 전달
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // HTTP 400 Bad Request 응답
    }
}
