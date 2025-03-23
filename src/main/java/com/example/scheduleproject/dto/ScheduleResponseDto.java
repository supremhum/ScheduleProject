package com.example.scheduleproject.dto;

import com.example.scheduleproject.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String author;
    private String title;
    private String createDate;
    private String updateDate;


    public ScheduleResponseDto(Long id, Schedule schedule) {
        this.id = id;
        this.author = schedule.getAuthor();
        this.title = schedule.getTitle();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
