package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleForm;
import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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

    // 게시글 생성
    public ArticleDto create(Long memberId, ArticleForm articleForm) {
        // 작성자가 데이터베이스에 존재하는지 확인한 다음, 존재하지 않는다면 예외를 발생시킴
        // 존재한다면 ArticleForm에 들어있는 정보로 게시글 엔티티 객체로 만들고
        // 게시글 리포지터리의 save 메소드를 사용해 저장한 후
        // 그 결과를 다시 DTO 객체로 변환해 반환
        Member member = memberRepository.findById(memberId).orElseThrow();

        Article article = Article.builder()
                .title(articleForm.getTitle())
                .description(articleForm.getDescription())
                .member(member)
                .build();
        articleRepository.save(article);

        // 일반적으로 자동 할당된 ID를 포함한 DTO 객체를 반환하도록 구현
        // (확장을 대비하여 서비스는 보통 컨트롤러와 독립적으로 설계)
        return mapToMemberDto(article);
    }

    // 게시글 수정
    public ArticleDto update(Long memberId, ArticleForm articleForm) throws BadRequestException {
        // 기존에 작성된 게시글이 없다면 잘못된 요청이므로 예외를 발생시키고,
        // 존재하는 게시글이라면 제목과 본문의 내용을 게시글 DTO 객체에 들어있는 내용으로 교체해 리포지터리에 저장
        Article article = articleRepository.findById(articleForm.getId()).orElseThrow();

        // (보안 체크) 수정하려는 게시글이 로그인한 사용자에 의해 작성되었는지 확인
        if (!article.getMember().getId().equals(memberId)) {
            throw new BadRequestException();
        }

        article.setTitle(articleForm.getTitle());
        article.setDescription(articleForm.getDescription());
        articleRepository.save(article);

        return mapToMemberDto(article);
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
