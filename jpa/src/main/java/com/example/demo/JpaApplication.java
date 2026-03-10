package com.example.demo;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JpaApplication implements ApplicationRunner {
    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member member1 = Member.builder()
                .name("윤서준")
                .email("SeojunYoon@goodee.co.kr")
                .age(10)
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .name("윤광철")
                .email("KwangcheolYoon@goodee.co.kr")
                .age(43)
                .build();
        memberRepository.save(member2);
    }
}
