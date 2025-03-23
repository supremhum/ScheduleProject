package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl (ScheduleRepository scheduleRepository) {
        this.scheduleRepository=scheduleRepository;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getAuthor(), dto.getTitle(), dto.getPassword());

        ScheduleResponseDto responseDto = scheduleRepository.saveSchedule(schedule);
        return responseDto;
//        return scheduleRepository.saveSchedule(schedule);

    }
}
