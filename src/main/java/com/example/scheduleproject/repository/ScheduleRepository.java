package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.MemberResponseDto;
import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Member;
import com.example.scheduleproject.entity.Schedule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<Schedule> findAllSchedules(Map<Object,Object> authorUpdateMap);
    Optional<Schedule> findScheduleById(Long id);
    int updateScheduleById(Long id, String author, String title);
    int updateTitleById(Long id, String title);
    int deleteScheduleById(Long id);
    Schedule findScheduleByIdOrElseThrow(Long id);
    Schedule findPasswordById(Long id);

    MemberResponseDto saveMember(Member member);
    Member findMemberByIdOrElseThrow(Long id);
}
