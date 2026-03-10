package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring Data JPA가 JpaRepository를 확장한 인터페이스를 자동으로 구현 객체로 만들고 스프링 빈 객체로 등록
// 생략 가능하지만 해당 인터페이스가 리포지터리라는 사실을 명시적으로 나타내는 것을 권장
@Repository
// JpaRepository<Member, Long>
// Member: 리포지터리가 관리하는 엔티티 타입 -> Member 엔티티에 대한 CRUD
// Long: 해당 엔티티의 아이디(PK) 타입
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Query Method 방식
    // 1. 검색 명명 규칙: findBy + 컬럼 이름(엔티티의 필드명 기준)
    // 이름으로 회원 조회
    List<Member> findAllByName(String name);
    List<Member> findByName(String name); // 주로 사용
    List<Member> findByNameIs(String name);
    List<Member> findByNameEquals(String name);

    // (참고) find 이외에도 get, query, read, search 사용 가능
    List<Member> getByName(String name);
    List<Member> queryByName(String name);
    List<Member> readByName(String name);
    List<Member> searchByName(String name);

    // 2. 두 가지 이상의 조건 검색
    // 이름과 이메일을 AND 조건으로 회원 조회
    List<Member> findByNameAndEmail(String name, String email);

    // 이름과 이메일을 OR 조건으로 회원 조회
    List<Member> findByNameOrEmail(String name, String email);

    // 이름의 시작으로 회원 조회("윤%")
    List<Member> findByNameStartingWith(String name);

    // 이름의 마지막으로 회원 조회("%윤")
    List<Member> findByNameEndingWith(String name);

    // 이름을 포함하는 회원 조회("%윤%")
    // 예: findByNameContaining("윤");
    List<Member> findByNameContaining(String name);

    // 이름을 포함하는 회원 조회로 LIKE 검색을 위한 와일드카드(%, _)를 직접 사용
    // 예: findByNameLike("%윤%");
    List<Member> findByNameLike(String name);
    
    // 3. 크기 비교
    // 나이 정보가 존재하지 않는 회원 조회
    List<Member> findByAgeIsNull();

    // 나이 정보가 존재하는 회원 조회
    List<Member> findByAgeIsNotNull();

    // 매개변수로 전달된 나이로 회원 조회
    List<Member> findByAgeIs(Integer age);
    List<Member> findByAge(Integer age); // Is 생략 가능

    // 매개변수로 전달된 나이보다 나이가 더 많은 회원 조회
    List<Member> findByAgeGreaterThan(Integer age);
    List<Member> findByAgeAfter(Integer age); // After는 주로 날짜/시간 타입 비교에 사용

    // 매개변수로 전달된 나이보다 나이가 더 적은 회원 조회
    List<Member> findByAgeLessThan(Integer age);
    List<Member> findByAgeBefore(Integer age); // Before는 주로 날짜/시간 타입 비교에 사용

    // 매개변수로 전달된 나이보다 나이가 더 적거나 같은 회원 조회
    List<Member> findByAgeLessThanEqual(Integer age);

    // 매개변수로 전달된 나이를 포함해 그 사이 나이의 회원 조회
    List<Member> findByAgeBetween(Integer min, Integer max);

    // Quiz: Query Method 작성
    // SELECT *
    // FROM member
    // WHERE name = ‘…’ AND email = ‘…’ OR age > …














}
