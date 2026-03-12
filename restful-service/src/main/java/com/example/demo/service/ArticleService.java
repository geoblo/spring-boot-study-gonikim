package com.example.demo.service;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());

        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .description(articleRequest.getDescription())
                .member(member)
                .build();
        articleRepository.save(article);

        return mapToArticleResponse(article);
    }

    // 게시글 전체 목록
    public List<ArticleResponse> findAll() {
        // 모든 게시글 목록을 조회하고 자바 스트림 API를 사용해
        // 게시글 엔티티 객체를 응답 DTO 객체로 매핑한 후, 다시 리스트로 만들어 반환
        return articleRepository.findAll()
                .stream()
                .map(article -> mapToArticleResponse(article))
                .toList();
    }

    // 특정 회원이 작성한 게시글 목록
    public List<ArticleResponse> findByMemberId(Long memberId) {
        // 회원 아이디로 회원 정보를 조회한 후, 해당 회원이 작성한 게시글 목록을 조회
        // 게시글 엔티티 객체를 응답 DTO 객체로 매핑한 후, 다시 리스트로 만들어 반환
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException());

        return articleRepository.findByMember(member)
                .stream()
                .map(article -> mapToArticleResponse(article))
                .toList();
    }

    // 게시글 조회
    public ArticleResponse findById(Long id) {
        // 게시글 아이디로 게시글 정보를 조회한 후, 게시글 엔티티 객체를 응답 DTO 객체로 매핑해서 반환
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException());

        return mapToArticleResponse(article);
    }

    // 게시글 수정
    public ArticleResponse update(Long id, ArticleRequest articleRequest) {
        // 게시글 아이디로 게시글 정보를 조회한 후, 게시글 엔티티 객체의 제목과 내용을 수정하고 저장
        // 수정된 게시글 엔티티 객체를 응답 DTO 객체로 매핑해서 반환
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException());

        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        
        // 수정한 것은 제목과 본문뿐이지만, JPA Auditing 기능을 사용해 수정 날짜를 자동으로 업데이트
        articleRepository.save(article);

        return mapToArticleResponse(article);
    }

    // 게시글 삭제
    public void delete(Long id) {
        // 실제 존재하는 게시글인지 검증한 후 리포지터리를 사용해 삭제
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException());

        articleRepository.delete(article);
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
