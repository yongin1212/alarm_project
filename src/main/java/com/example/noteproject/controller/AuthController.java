package com.example.noteproject.controller;

import com.example.noteproject.dto.request.LoginReqDto;
import com.example.noteproject.dto.request.SignupReqDto;
import com.example.noteproject.dto.response.AuthResDto;
import com.example.noteproject.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<AuthResDto> login(@Valid @RequestBody LoginReqDto req){
        AuthResDto res = authService.login(req);
        if(res.isSuccess()){
            return ResponseEntity.ok(res);
        }
       return ResponseEntity.status(401).body(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResDto> signup(@Valid @RequestBody SignupReqDto req){
        AuthResDto res = authService.signup(req);
        if(res.isSuccess()){
            return ResponseEntity.status(201).body(res);
        }
        return ResponseEntity.badRequest().body(res);
    }
}
