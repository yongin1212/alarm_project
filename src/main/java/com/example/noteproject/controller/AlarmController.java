package com.example.noteproject.controller;

import com.example.noteproject.dto.request.AlarmPostReqDto;
import com.example.noteproject.dto.response.AlarmPostResDto;
import com.example.noteproject.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/post")
    private ResponseEntity<AlarmPostResDto> createAlarm(@ModelAttribute AlarmPostReqDto req){
        AlarmPostResDto resDto = alarmService.createAlarm(req);
        if (resDto.isSuccess()){
            return ResponseEntity.ok(resDto);
        }
        return ResponseEntity.badRequest().body(resDto);
    }

}
