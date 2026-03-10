package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Spring Data JPA가 JpaRepository를 확장한 인터페이스를 자동으로 구현 객체로 만들고 스프링 빈 객체로 등록
// 생략 가능하지만 해당 인터페이스가 리포지터리라는 사실을 명시적으로 나타내는 것을 권장
@Repository
// JpaRepository<Member, Long>
// Member: 리포지터리가 관리하는 엔티티 타입 -> Member 엔티티에 대한 CRUD
// Long: 해당 엔티티의 아이디(PK) 타입
public interface MemberRepository extends JpaRepository<Member, Long> {
}
