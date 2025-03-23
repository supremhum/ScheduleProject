package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    Optional<Schedule> findScheduleById(Long id);
    int updateScheduleById(Long id, String author, String title);
    int updateTitleById(Long id, String title);
}
