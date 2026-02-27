package com.example.noteproject.service;

import com.example.noteproject.dto.request.LoginReqDto;
import com.example.noteproject.dto.request.SignupReqDto;
import com.example.noteproject.dto.response.LoginResDto;
import com.example.noteproject.dto.response.SignupResDto;
import com.example.noteproject.entity.User;
import com.example.noteproject.jwt.JwtUtil;
import com.example.noteproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResDto signup(SignupReqDto req){
        if(req.getUsername() == null || req.getPassword() == null || req.getPassword().equals(req.getUsername())){
            SignupResDto res = SignupResDto.builder()
                    .message("양식에 맞게 입력해주세요.")
                    .success(false)
                    .build();
            return res;
        }

        if(userRepository.existsByUsername(req.getUsername())){
            SignupResDto res = SignupResDto.builder()
                    .message("이미 존재하는 회원입니다.")
                    .success(false)
                    .build();
            return res;
        }

        String encodedPassword = passwordEncoder.encode(req.getPassword());

        User user = User.builder()
                .username(req.getUsername())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
        SignupResDto res = SignupResDto.builder()
                .message("환영합니다.")
                .success(true)
                .build();
        return res;
    }

    public LoginResDto login(LoginReqDto req){
        Optional<User> userOpt = userRepository.findByUsername(req.getUsername());

        String accessToken = jwtUtil.generateAccessToken(req.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(req.getUsername());

        if(userOpt.isEmpty()){
            LoginResDto res = LoginResDto.builder()
                    .accesstoken(null)
                    .refreshtoken(null)
                    .message("등록되지 않은 사용자")
                    .success(false)
                    .build();
            return res;
        }

        User user = userOpt.get();

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){            LoginResDto res = LoginResDto.builder()
                .accesstoken(null)
                .refreshtoken(null)
                .message("비밀번호가 일치하지 않습니다.")
                .success(false)
                .build();
            return res;
        }

        LoginResDto res = LoginResDto.builder()
                .accesstoken(accessToken)
                .refreshtoken(refreshToken)
                .message("로그인에 성공했습니다.")
                .success(true)
                .build();
        return res;
    }
}