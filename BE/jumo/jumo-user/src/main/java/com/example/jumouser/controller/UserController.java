package com.example.jumouser.controller;


import com.example.jumouser.dto.LoginRequestDto;
import com.example.jumouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto requestDto){

    }
}
