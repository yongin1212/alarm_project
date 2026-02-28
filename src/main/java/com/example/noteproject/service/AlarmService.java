package com.example.noteproject.service;

import com.example.noteproject.dto.request.AlarmPostReqDto;
import com.example.noteproject.dto.response.AlarmGetResDto;
import com.example.noteproject.dto.response.AlarmPostResDto;
import com.example.noteproject.entity.Alarm;
import com.example.noteproject.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

    private final AlarmRepository repo;

    public AlarmPostResDto createAlarm(AlarmPostReqDto req) {

        LocalDateTime alarmTime = req.alarmTime();
        try {
            Alarm alarm = Alarm.builder()
                    .song(req.song() != null ? req.song().getBytes() : null)
                    .songName(req.songName())
                    .alarmName(req.alarmName())
                    .alarmTime(alarmTime)
                    .build();

            Alarm saved = repo.save(alarm);

            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, alarmTime);
            long diffMinutes = duration.toMinutes();

            String timeMessage;
            if (diffMinutes >= 1440) {
                long days = diffMinutes / 1440;
                long hours = (diffMinutes % 1440) / 60;
                long mins = diffMinutes % 60;
                timeMessage = days + "일 " + hours + "시간 " + mins + "분";
            } else if (diffMinutes >= 60) {
                timeMessage = (diffMinutes / 60) + "시간 " + (diffMinutes % 60) + "분";
            } else {
                timeMessage = diffMinutes + "분";
            }

            AlarmPostResDto response = AlarmPostResDto.builder()
                    .success(true)
                    .message(timeMessage + "후에 알람이 울립니다.")
                    .build();

            return response;

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    public AlarmGetResDto getAlarm(Long id) {
        Alarm alarm = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 알람을 찾을 수 없습니다. ID: " + id));
        AlarmGetResDto res = AlarmGetResDto.builder()
                .id(alarm.getId())
                .alarmName(alarm.getAlarmName())
                .alarmTime(alarm.getAlarmTime())
                .build();

        return res;
    }
}
