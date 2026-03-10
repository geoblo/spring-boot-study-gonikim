package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 쿼리 결과를 담으려면 필수
@NoArgsConstructor
public class MemberStats {
    private String name;
    private String email;
    private Long count;
}
