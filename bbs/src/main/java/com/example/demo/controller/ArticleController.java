package com.example.demo.controller;

import com.example.demo.dto.ArticleDto;
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
    public String getArticle(@RequestParam("id") Long id, Model model) {
        model.addAttribute("article", articleService.findById(id));

        return "article/article-content";
    }




}
