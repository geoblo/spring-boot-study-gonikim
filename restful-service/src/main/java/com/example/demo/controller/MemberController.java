package com.example.demo.controller;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 리소스가 생성되었음을 알려 주기 위해 HTTP 상태 코드로 201을 전달
    public List<MemberResponse> postMembers(@RequestBody List<MemberRequest> memberRequests) {
        return memberService.createMembers(memberRequests);
    }

    @GetMapping
    public List<MemberResponse> getAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public MemberResponse get(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    @PutMapping("/{id}")
    public MemberResponse put(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
        return memberService.update(id, memberRequest);
    }

    @PatchMapping("/{id}")
    public MemberResponse patch(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
        return memberService.patch(id, memberRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        memberService.deleteById(id);
    }

    @PostMapping("/{id}/articles")
    @ResponseStatus(HttpStatus.CREATED) // 201 Created
    public ArticleResponse postArticle(@PathVariable("id") Long memberId, @RequestBody ArticleRequest articleRequest) {
        return articleService.create(memberId, articleRequest);
    }

    // 특정 회원이 작성한 게시글 목록 조회
    // 1. 리다이렉트 예시
//    @GetMapping("/{id}/articles")
//    public void getArticles(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
//        response.sendRedirect("/api/articles?memberId=" + id);
//    }

    // 2. 포워딩 예시
    @GetMapping("/{id}/articles")
    public void getArticles(
            @PathVariable("id") Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        request.getRequestDispatcher("/api/articles?memberId=" + id)
                .forward(request, response);
    }












}
