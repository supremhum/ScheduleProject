package com.example.scheduleproject.entity;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {

    private Long memberId;
    private String name;
    private String email;
    private String createDate;
    private String updateDate;

    public Member(String name,String email) {
        this.name = name;
        this.email = email;
    }

}
