package com.example.scheduleproject.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String writer;
    private String title;
    private String password;
    private String create_date;
    private String update_date;
}
