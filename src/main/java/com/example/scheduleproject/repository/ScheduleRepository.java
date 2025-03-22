package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;

public interface ScheduleRepository {
    public ScheduleResponseDto saveSchedule(Schedule schedule);
}
