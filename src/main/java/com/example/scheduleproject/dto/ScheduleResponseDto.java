package com.example.scheduleproject.dto;

import com.example.scheduleproject.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String writer;
    private String title;
//    private String create_date;
//    private String update_date;

//    public ScheduleResponseDto(Schedule schedule) {
//
//        this.id = schedule.getId();
//        this.writer = schedule.getWriter();
//        this.title = schedule.getTitle();
//        this.create_date = schedule.getCreate_date();
//        this.update_date = schedule.getUpdate_date();
//
//    }

    public ScheduleResponseDto(Long id, Schedule schedule) {
        this.id = id;
        this.writer = schedule.getWriter();
        this.title = schedule.getTitle();
//        this.create_date = schedule.getCreate_date();
//        this.update_date = schedule.getUpdate_date();
    }
}
