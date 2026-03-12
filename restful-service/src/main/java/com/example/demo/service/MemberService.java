package com.example.demo.service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 정보를 생성
    public MemberResponse create(MemberRequest memberRequest) {
        Member member = Member.builder()
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .age(memberRequest.getAge())
                .enabled(true) // default enabled is true
                .build();
        memberRepository.save(member);

        return mapToMemberResponse(member);
    }

    // 회원 목록 전체 조회
    public List<MemberResponse> findAll() {
        // MemberResponse DTO 객체로 변환하고, 이를 다시 리스트에 추가
        return memberRepository.findAll() // 가져온 회원 리스트에
                .stream() // 회원 엔티티 객체를 하나씩 스트림으로 보내고
                .map(member -> mapToMemberResponse(member)) // 새로운 DTO 객체로 매핑한 후
                .toList(); // 다시 리스트로 만듦
    }

    // 회원 아이디를 사용해 정보를 조회
    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundException());

        return mapToMemberResponse(member);
    }

    // 회원 정보 수정
    public MemberResponse update(Long id, MemberRequest memberRequest) {
        // JPA 철학은 엔티티 객체 상태 변경 -> 변경 감지(dirty checking)
        // 회원 아이디로 회원 정보를 조회한 후, 수정할 회원 정보가 존재하지 않으면 NotFoundException 예외를 던짐
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundException());

        member.setName(memberRequest.getName());
        member.setEmail(memberRequest.getEmail());
        member.setAge(memberRequest.getAge());
        memberRepository.save(member);

        return mapToMemberResponse(member);
    }

    // 회원 정보 일부 수정
    public MemberResponse patch(Long id, MemberRequest memberRequest) {
        // 전달된 회원 아이디로 정보를 검색한 후 DTO 객체에서 null이 아닌 필드에 대해서만
        // 엔티티 객체를 업데이트하고 저장한 후 결과를 다시 DTO 객체로 변환해 반환
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundException());

        // 전달된 값이 있는 필드만 업데이트
        if (memberRequest.getName() != null) member.setName(memberRequest.getName());
        if (memberRequest.getEmail() != null) member.setEmail(memberRequest.getEmail());
        if (memberRequest.getAge() != null) member.setAge(memberRequest.getAge());
        memberRepository.save(member);

        return mapToMemberResponse(member);
    }

    // 회원 정보 삭제
    public void deleteById(Long id) {
        // 회원 아이디로 정보를 검색한 후, 존재하지 않으면 NotFoundException 예외를 던지고
        // 존재하면 해당 회원 정보를 삭제
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundException());
        memberRepository.delete(member);

        // 또 다른 방법: ID로 바로 삭제하는 deleteById() 메소드 사용
        // deleteById() 메소드는 내부적으로 먼저 해당 ID로 회원 정보를 조회한 후,
        // 존재하지 않으면 EmptyResultDataAccessException 예외를 던지고, 존재하면 해당 회원 정보를 삭제
        // memberRepository.deleteById(id);
    }

    private MemberResponse mapToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .build();
    }
}
