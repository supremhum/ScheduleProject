package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.MemberRequestDto;
import com.example.scheduleproject.dto.MemberResponseDto;
import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.OK);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponseDto> createMember(@Valid @RequestBody MemberRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveMember(dto), HttpStatus.OK);
    }

    @GetMapping("/schedules") // api에 yyyy-mm-dd 임을 표기해주자
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(ScheduleRequestDto dto) {

        return new ResponseEntity<>(scheduleService.findAllSchedules(dto), HttpStatus.OK);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findMemberById(id), HttpStatus.OK);
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@PathVariable("id") Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateScheduleById(id, dto.getAuthor(), dto.getTitle(),dto.getPassword()), HttpStatus.OK);
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateTitleById(@PathVariable("id") Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateTitleById(id, dto.getAuthor(), dto.getTitle(),dto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable("id") Long id, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteScheduleById(id,dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
