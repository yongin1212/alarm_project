package com.example.noteproject.controller;

import com.example.noteproject.dto.request.LoginReqDto;
import com.example.noteproject.dto.request.SignupReqDto;
import com.example.noteproject.dto.response.LoginResDto;
import com.example.noteproject.dto.response.SignupResDto;
import com.example.noteproject.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto req){
        LoginResDto res = authService.login(req);
        if(res.isSuccess()){
            return ResponseEntity.ok(res);
        }
       return ResponseEntity.status(401).body(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResDto> signup(@Valid @RequestBody SignupReqDto req){
        SignupResDto res = authService.signup(req);
        if(res.isSuccess()){
            return ResponseEntity.status(201).body(res);
        }
        return ResponseEntity.badRequest().body(res);
    }
}
