package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    // 패스워드는 웹 화면에서 사용하지 않으므로 제외
}
