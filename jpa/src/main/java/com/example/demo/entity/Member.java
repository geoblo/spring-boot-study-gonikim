package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 모델(도메인) 객체: 데이터를 관리할 목적의 객체(예: DTO, VO, Entity)
@Entity // 객체를 데이터베이스의 테이블과 연결(연동)하기 위해 사용(엔티티 객체로 정의)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id // 아이디(PK)로 사용하는 프로퍼티에 사용(조회, 수정, 삭제 시 기준이 됨)
    @GeneratedValue // ID 생성 전략 선언, 애플리케이션이 아니라 데이터베이스에서 자동으로 생성된 값이 할당됨을 알려줌
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
