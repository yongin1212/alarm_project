package com.example.noteproject.service;

import com.example.noteproject.dto.request.LoginReqDto;
import com.example.noteproject.dto.request.SignupReqDto;
import com.example.noteproject.dto.response.AuthResDto;
import com.example.noteproject.entity.User;
import com.example.noteproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResDto signup(SignupReqDto req){
        AuthResDto res = new AuthResDto();
        if(req.getUsername() == null || req.getPassword() == null || req.getPassword().equals(req.getUsername())){
            res.setMessage("양식에 맞게 입력해주세요.");
            res.setSuccess(false);
            return res;
        }

        if(userRepository.existsByUsername(req.getUsername())){
            res.setMessage("이미 존재하는 회원입니다.");
            res.setSuccess(false);
            return res;
        }

        String encodedPassword = passwordEncoder.encode(req.getPassword());

        User user = User.builder()
                .username(req.getUsername())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
        res.setMessage("환영합니다.");
        res.setSuccess(true);

        return res;
    }

    public AuthResDto login(LoginReqDto req){
        AuthResDto res = new AuthResDto();

        Optional<User> userOpt = userRepository.findByUsername(req.getUsername());

        if(userOpt.isEmpty()){
            res.setMessage("등록되지 않은 사용자");
            res.setSuccess(false);
            return res;
        }

        User user = userOpt.get();

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            res.setMessage("비밀번호가 일치하지 않습니다");
            res.setSuccess(false);
            return res;
        }

        res.setMessage("로그인이 완료되었습니다");
        res.setSuccess(true);
        return res;
    }
}