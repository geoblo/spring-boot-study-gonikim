package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 모델(도메인) 객체: 데이터를 관리할 목적의 객체(예: DTO, VO, Entity)
@Entity // 객체를 데이터베이스의 테이블과 연결(연동)하기 위해 사용(엔티티 객체로 정의)
//@Table(name = "vip_member") // DB 테이블명이 다르면 반드시 필요
@Table(indexes = { // 인덱스 설정
        @Index(name = "idx_name_age", columnList = "name, age"), // 복합 인덱스(컬럼 순서가 매우 중요)
        @Index(name = "idx_index", columnList = "email")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    /*@Id // 아이디(PK)로 사용하는 프로퍼티에 사용(조회, 수정, 삭제 시 기준이 됨)
//    @GeneratedValue // ID 생성 전략 선언, 애플리케이션이 아니라 데이터베이스에서 자동으로 생성된 값이 할당됨을 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스의 IDENTITY 컬럼 기능을 사용하여 기본 키를 생성
    private Long id;
    @Column(name = "display_name")
    private String name;
    @Column(name = "primary_contact")
    private String email;
    private Integer age;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128, nullable = false)
    private String name;
    @Column(length = 256, nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 10")
    private Integer age;
    @Transient // 해당 프로퍼티가 테이블과 매핑되지 않도록 설정
    private String address; // 순수하게 애플리케이션 내부에서만 사용하는 값
}
