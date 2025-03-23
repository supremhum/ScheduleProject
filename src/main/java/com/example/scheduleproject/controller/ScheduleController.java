package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService=scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule (@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto),HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules () {
        return new ResponseEntity<>(scheduleService.findAllSchedules(),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id),HttpStatus.OK);

    }

}
