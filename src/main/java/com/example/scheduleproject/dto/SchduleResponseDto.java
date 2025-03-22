package com.example.scheduleproject.dto;

import com.example.scheduleproject.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchduleResponseDto {
    private Long id;
    private String writer;
    private String title;
    private String create_date;
    private String update_date;

    public SchduleResponseDto(Schedule schedule) {

        this.id = schedule.getId();
        this.writer = schedule.getWriter();
        this.title = schedule.getTitle();
        this.create_date = schedule.getCreate_date();
        this.update_date = schedule.getUpdate_date();

    }
}
