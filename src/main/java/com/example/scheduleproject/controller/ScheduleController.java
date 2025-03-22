package com.example.scheduleproject.controller;

import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.service.ScheduleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService=scheduleService;
    }

}
