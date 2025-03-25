package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.MemberRequestDto;
import com.example.scheduleproject.dto.MemberResponseDto;
import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules(ScheduleRequestDto dto);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateScheduleById(Long id, String author, String title, String password);
    ScheduleResponseDto updateTitleById(Long id, String author, String title,String password);
    void deleteScheduleById(Long id,String password);

    MemberResponseDto saveMember(MemberRequestDto dto);
    MemberResponseDto findMemberById(Long id);
}
