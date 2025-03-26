package com.example.scheduleproject.entity;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Long memberId;
    private String author;
    private String title;
    private String password;
    private String createDate;
    private String updateDate;

    // id,생성-수정날짜 제외한 생성자
    public Schedule(String author, String title, String password) {
        this.author = author;
        this.title = title;
        this.password = password;
    }

    // 때에 맞춰서 생성
    public Schedule(Long id, String author, String title, String createDate, String updateDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    // 때에 맞춰서 생성
    public Schedule(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    // 때에 맞춰서 생성
    public Schedule(Long id,Long memberId, String author, String title, String createDate, String updateDate) {
        this.id = id;
        this.memberId = memberId;
        this.author = author;
        this.title = title;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Schedule(Long memberId,String author, String title, String password) {
        this.memberId = memberId;
        this.author = author;
        this.title = title;
        this.password = password;
    }
}
