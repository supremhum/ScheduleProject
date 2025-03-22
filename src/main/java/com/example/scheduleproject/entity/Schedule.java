package com.example.scheduleproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String writer;
    private String title;
    private String password;
    private String create_date;
    private String update_date;

    // id 제외한 생성자
    public Schedule(String writer, String title, String password, String create_date, String update_date) {
        this.writer = writer;
        this.title = title;
        this.password = password;
        this.create_date = create_date;
        this.update_date = update_date;
    }

    // 때에 맞춰서 기능 생성
}
