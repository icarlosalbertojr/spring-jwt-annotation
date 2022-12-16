package com.icarlosalbertojr.springjwtannotation.controllers;

import com.icarlosalbertojr.springjwtannotation.annotations.UncheckJwt;
import com.icarlosalbertojr.springjwtannotation.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    @UncheckJwt
    @PostMapping
    public String auth() {
        return authService.authenticate("email", "senha");
    }

    @GetMapping("/login")
    public String get() {
        return "Hello";
    }

}
