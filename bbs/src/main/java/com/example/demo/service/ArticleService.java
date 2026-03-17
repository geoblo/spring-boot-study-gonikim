package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    // 게시글 목록
    public List<ArticleDto> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(article -> mapToMemberDto(article))
                .toList();
    }
    // 게시글 목록(+페이징 처리)
    public Page<ArticleDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(article -> mapToMemberDto(article));
        // 이때 반환되는 Page 객체에는 게시글 목록뿐만 아니라
        // 페이지네이션을 구성하는 데 필요한 모든 정보가 들어 있음
    }

    // 게시글 상세 보기
    public ArticleDto findById(Long id) {
        return articleRepository.findById(id)
                .map(article -> mapToMemberDto(article))
                .orElseThrow();
    }

    // 게시글 엔티티 객체를 DTO 객체로 변환하는 기능
    public ArticleDto mapToMemberDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .created(article.getCreated())
                .updated(article.getUpdated())
                .memberId(article.getMember().getId()) // 게시글과 회원이 관계 매핑되어 있어 게시글에서 회원의 아이디를 조회할 수 있음
                .name(article.getMember().getName()) // 게시글에서 회원의 이름을 조회할 수 있음
                .email(article.getMember().getEmail()) // 게시글에서 회원의 이메일을 조회할 수 있음
                .build();
    }


}
