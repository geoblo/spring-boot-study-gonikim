package com.example.demo.controller;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleForm;
import com.example.demo.dto.MemberForm;
import com.example.demo.dto.PasswordForm;
import com.example.demo.security.MemberUserDetails;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    // 게시글 목록
    @GetMapping("/list")
//    public String getArticleList(Model model) {
//        List<ArticleDto> articles = articleService.findAll();
//        model.addAttribute("articles", articles);
//
//        return "article/article-list";
//    }
    // 게시글 목록(페이지네이션 적용)
//    @GetMapping("/list")
    public String getArticleList(
            // @PageableDefault: URL에 페이지 정보가 없을 때 디폴트 페이지 객체를 생성할 수 있도록 해줌
            // 예: http://localhost:8080/article/list?page=0&size=10&sort=id,desc
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        Page<ArticleDto> page = articleService.findAll(pageable);
        model.addAttribute("page", page);

        return "article/article-list";
    }

    // 게시글 상세 보기
    @GetMapping("/content")
    public String getArticle(@RequestParam("id") Long id, Model model,
                             @AuthenticationPrincipal MemberUserDetails userDetails) {
        log.info("userDetails: {}", userDetails);

        ArticleDto article = articleService.findById(id);

        // 방법2: 컨트롤러에서 로그인한 사용자가 게시글 작성자 본인이 맞는지 확인
        boolean isOwner = false;
        if (userDetails != null) {
            isOwner = userDetails.getMemberId().equals(article.getMemberId());
        }

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("article", article);

        return "article/article-content";
    }

    // 게시글 작성 화면
    @GetMapping("/add")
    // @ModelAttribute:
    // 1) 요청 파라미터와 자바 객체를 매핑
    // 2) 자동으로 Model 객체에 attribute로 추가되어 뷰로 전달됨
    public String getArticleAdd(@ModelAttribute("article") ArticleForm articleForm) {
        // 초기 입력 값 설정
        articleForm.setDescription("바르고 고운말을 사용하여 주세요^^");

        return "article/article-add";
    }

    // 게시글 작성 처리(+입력 폼 검증 추가)
    @PostMapping("/add")
    public String postArticleAdd(
            @Valid @ModelAttribute("article") ArticleForm articleForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberUserDetails userDetails) {
        // BindingResult는 검증 대상이 되는 항목을 담고 있는 targets와
        // 검증 결과에 따라 오류 내용을 담고 있는 errors로 구성됨

        // 검증 어노테이션 외에 컨트롤러에서 별도의 로직으로 검증하고 오류를 추가하고 싶을 때
        // rejectValue(필드, 에러 코드, 에러 메시지) 제공
        if (articleForm.getTitle() != null && articleForm.getTitle().contains("T발")) {
            bindingResult.rejectValue("title", "SlangDetected", "욕설을 사용하지 마세요");
        }

        if (articleForm.getDescription() != null && articleForm.getDescription().contains("T발")) {
            bindingResult.rejectValue("description", "SlangDetected", "욕설을 사용하지 마세요");
        }

        // 검증 오류가 발생했는지 확인
        if (bindingResult.hasErrors()) {
            // 다시 입력 폼으로 돌아가도록 뷰 이름을 반환
            // 이때 bindingResult도 자동으로 Model에 들어가므로 
            // bindingResult에 포함된 오류 메시지들을 뷰에서 접근하여 표시할 수 있음
            return "article/article-add";
        }

        articleService.create(userDetails.getMemberId(), articleForm);

        return "redirect:/article/list";
    }

    // 게시글 수정 화면
    @GetMapping("/edit")
    public String getArticleEdit(@ModelAttribute("article") ArticleForm articleForm) {
        ArticleDto articleDto = articleService.findById(articleForm.getId());
        articleForm.setId(articleDto.getId());
        articleForm.setTitle(articleDto.getTitle());
        articleForm.setDescription(articleDto.getDescription());

        return "article/article-edit";
    }
    
    // 게시글 수정 처리
    @PostMapping("/edit")
    public String postArticleEdit(
            @Valid @ModelAttribute("article") ArticleForm articleForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            return "article/article-edit";
        }

        articleService.update(userDetails.getMemberId(), articleForm);

        return "redirect:/article/content?id=" + articleForm.getId();
    }















}
