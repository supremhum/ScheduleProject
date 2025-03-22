package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;

public interface ScheduleService {

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
}
