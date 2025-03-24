package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.OK);

    }

    @GetMapping // api에 yyyy-mm-dd 임을 표기해주자
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String author,
                                                                      @RequestParam(required = false) String updateDate,
                                                                      @RequestParam(required = false) Long id,
                                                                      @RequestParam(required = false) String title,
                                                                      @RequestParam(required = false) String createDate

    ) {
        Map<Object,Object> authorUpdateMap = new HashMap<>();
        if (author!=null) {
        authorUpdateMap.put("author",author);}
        if (updateDate!=null) {
        authorUpdateMap.put("updateDate",updateDate);}
        if (id!=null) {
            authorUpdateMap.put("id",id);}
        if (createDate!=null) {
            authorUpdateMap.put("createDate",createDate);}
        if (title!=null) {
            authorUpdateMap.put("title",title);}
        return new ResponseEntity<>(scheduleService.findAllSchedules(authorUpdateMap), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@PathVariable("id") Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateScheduleById(id, dto.getAuthor(), dto.getTitle(),dto.getPassword()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateTitleById(@PathVariable("id") Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateTitleById(id, dto.getAuthor(), dto.getTitle(),dto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable("id") Long id) {
        scheduleService.deleteScheduleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
