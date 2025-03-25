package com.example.scheduleproject.dto;

import com.example.scheduleproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String name;
    private String email;
    private String createDate;
    private String updateDate;

    public MemberResponseDto(Member member) {
        this.memberId=member.getMemberId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.createDate = member.getCreateDate();
        this.updateDate = member.getUpdateDate();
    }
}
