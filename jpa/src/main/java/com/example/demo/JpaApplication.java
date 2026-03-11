package com.example.demo;

import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional // 인위적으로 영속성 컨텍스트를 만들기 위해 추가
public class JpaApplication implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 영속성 컨텍스트 테스트
        /*
        Member member1 = Member.builder()
                .name("윤서준")
                .email("SeojunYoon@goodee.co.kr")
                .age(10)
                .build();
        log.info("save 윤서준");
        memberRepository.save(member1);
        log.info("saved {}", member1);

        Member member2 = Member.builder()
                .name("윤광철")
                .email("KwangcheolYoon@goodee.co.kr")
                .age(43)
                .build();
        log.info("save 윤광철");
        memberRepository.save(member2);
        log.info("saved {}", member2);

        log.info("read 윤서준");
        member1 = memberRepository.findById(member1.getId()).orElseThrow(); // 필요에 따라 조회 실행 시 flush 발생

        log.info("update 윤서준");
        member1.setAge(11);
        memberRepository.save(member1);
        member1.setAge(12);
        memberRepository.save(member1);
        member1.setAge(13);
        memberRepository.save(member1); // 영속성 컨텍스트에 의해 변경 감지(dirty checking)로 자동 업데이트 -> save 메서드 생략 가능
        log.info("updated {}", member1);

        log.info("update 윤광철");
        memberRepository.save(member2);
        log.info("updated {}", member2);
        
        log.info("애플리케이션 종료");
        */
        
        // 관계 매핑 테스트
        Member member1 = Member.builder()
                .name("윤서준")
                .email("SeojunYoon@goodee.co.kr")
                .age(10)
                .build();
        memberRepository.save(member1);

        Article acticle1 = Article.builder()
                .title("JPA란?")
                .description("JPA는 자바 ORM 기술입니다.")
                .member(member1) // 연관 관계 설정
                .build();
        articleRepository.save(acticle1);

        // 관계 매핑을 하면 프로퍼티로 존재하는 회원 정보까지 한 번에 조회할 수 있어 편리
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            log.info("{}", article);
        }

    }
}
