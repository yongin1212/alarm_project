package com.example.noteproject.controller;

import com.example.noteproject.dto.request.LoginReqDto;
import com.example.noteproject.dto.request.SignupReqDto;
import com.example.noteproject.dto.response.AuthResDto;
import com.example.noteproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody LoginReqDto req){
        AuthResDto res = authService.login(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResDto> signup(@RequestBody SignupReqDto req){
        AuthResDto res = authService.signup(req);
        return ResponseEntity.ok(res);
    }
}
