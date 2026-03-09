package com.prince.ems.controller;

import com.prince.ems.dto.login.*;
import com.prince.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request){
        return authService.login(request);
    }
}

