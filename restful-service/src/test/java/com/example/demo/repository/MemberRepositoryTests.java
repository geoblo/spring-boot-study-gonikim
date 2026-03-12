package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    // 테스트 시작 전 회원 리포지터리에 초기 데이터 생성
    @BeforeEach
    public void doBeforeEach() {
        // 각 테스트 전에 수행되어야 할 작업
        memberRepository.save(Member.builder().name("윤서준").email("SeojunYoon@hanbit.co.kr").age(10).enabled(true).build());
        memberRepository.save(Member.builder().name("윤광철").email("KwangcheolYoon@hanbit.co.kr").age(43).enabled(true).build());
        memberRepository.save(Member.builder().name("공미영").email("MiyeongKong@hanbit.co.kr").age(26).enabled(false).build());
        memberRepository.save(Member.builder().name("김도윤").email("DoyunKim@hanbit.co.kr").age(10).enabled(true).build());
    }

    // 각 테스트가 완료된 후에는 회원 리포지터리 삭제
    @AfterEach
    public void doAfterEach() {
        // 각 테스트가 종료된 후에 수행되어야 할 작업
        memberRepository.deleteAll();
    }

    @Test
    public void testUserCase1() {
        // 첫 번째 테스트 코드

        // 회원 리포지터리에 저장된 개수(회원 수)가 4인지 검증

        // '윤서준'이라는 이름으로 검색된 결과 개수가 1인지 검증

        // 이름이 '윤서준'이고 이메일이 'SeojunYoon@goodee.co.kr'인 사용자를 조회한 결과 개수가 1인지 검증

        // 이름이 '윤서준'이거나 또는 이메일이 'KwangcheolYoon@goodee.co.kr'인 사용자를 조회한 결과 개수가 2인지 검증

        // 이름에 '윤'이라는 글자가 포함된 사용자를 조회한 결과 개수가 3인지 검증

        // 이름이 '영'으로 끝나는 사람을 조회한 결과 개수가 1인지 검증

        // 나이가 26를 초과하는 사람의 수가 1인지 검증

        // 나이가 26세 이상인 사람의 수가 2인지 검증

        // 나이가 26세 미만인 사람의 수가 2인지 검증

        // 나이가 26세 이하인 사람의 수가 3인지 검증


    }

    @Test
    public void testUserCase2() {
        // 두 번째 테스트 코드
    }

    @Test
    public void testUserCase3() {
        // 세 번째 테스트 코드
    }


}
