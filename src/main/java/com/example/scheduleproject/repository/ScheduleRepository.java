package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
}
