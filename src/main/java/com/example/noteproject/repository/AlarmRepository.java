package com.example.noteproject.repository;

import com.example.noteproject.dto.response.AlarmGetResDto;
import com.example.noteproject.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm,Long> {
    boolean existsById(Long id);

   Optional<Alarm> findById(Long id);
}
