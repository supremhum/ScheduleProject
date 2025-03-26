package com.example.scheduleproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberRequestDto {
    @NotNull
    private String name;
    private String email;
}
