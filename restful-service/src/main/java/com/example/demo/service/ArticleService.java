package com.example.demo.service;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    // 게시글뿐만 아니라 작성자 정보도 필요하므로, 회원 리포지터리도 함께 선언
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    // 게시글 작성
    public ArticleResponse create(Long memberId, ArticleRequest articleRequest) {
        // 우선 회원이 실제로 존재하는지 확인하기 위해 회원 아이디로 회원 정보를 조회
        // 회원 정보가 존재하지 않으면 NotFoundException 예외를 던짐
        // 정상적으로 조회된다면, 게시글 엔티티 객체를 생성할 때 회원 엔티티 객체를 함께 설정
        // 게시글 엔티티 객체를 저장한 후, 응답 DTO 객체로 매핑해서 반환



        return null;


    }






    // 게시글 엔티티 객체를 응답 DTO 객체로 매핑하기 위한 메소드
    private ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .created(article.getCreated())
                .updated(article.getUpdated())
                .memberId(article.getMember().getId())
                .name(article.getMember().getName())
                .email(article.getMember().getEmail())
                .build();
    }
}
