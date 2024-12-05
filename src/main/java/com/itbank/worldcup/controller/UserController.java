package com.itbank.worldcup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
   @GetMapping("/login")
    public void login() {}

    @GetMapping("/join")
    public void join() {}

    @GetMapping("/approveList")
    public void approveList() {}

}
