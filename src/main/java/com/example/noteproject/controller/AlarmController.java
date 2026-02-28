package com.example.noteproject.controller;

import com.example.noteproject.dto.request.AlarmPostReqDto;
import com.example.noteproject.dto.response.AlarmGetResDto;
import com.example.noteproject.dto.response.AlarmPostResDto;
import com.example.noteproject.entity.Alarm;
import com.example.noteproject.service.AlarmService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/post")
    private ResponseEntity<AlarmPostResDto> createAlarm(@ModelAttribute AlarmPostReqDto req){
        AlarmPostResDto res = alarmService.createAlarm(req);
        if (res.isSuccess()){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().body(res);
    }

    @GetMapping("/get")
    public ResponseEntity<AlarmGetResDto> getAlarm(@RequestParam Long alarmId){
        AlarmGetResDto res = alarmService.getAlarm(alarmId);
        return ResponseEntity.ok(res);
    }
}
