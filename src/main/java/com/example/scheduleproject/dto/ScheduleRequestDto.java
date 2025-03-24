package com.example.scheduleproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleRequestDto {
    private String author;
    private String title;
    private String password;
    private Long id;
    private String createDate;
    private String updateDate;
}
