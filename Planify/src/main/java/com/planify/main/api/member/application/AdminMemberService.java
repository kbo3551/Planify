package com.planify.main.api.member.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planify.main.api.member.application.dto.MemberDTO;
import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.api.roles.domain.Roles;
import com.planify.main.api.roles.infrastructure.RolesRepository;

import lombok.RequiredArgsConstructor;
/**
 * 관리자 > 사용자 관리 및 권한 관리
 */
@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 목록 조회
     */
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "regDt"));
        return members.stream()
                .map(MemberDTO::ofEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자 상세 조회
     */
    public MemberDTO getMemberById(Long memberNo) {
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return MemberDTO.ofEntity(member);
    }
    
    /**
     * 신규 사용자 등록
     */
    @Transactional
    public void createMember(MemberDTO memberDTO) {
        // ID 중복 체크
        memberRepository.findByMemberId(memberDTO.getMemberId()).ifPresent(member -> {
            throw new IllegalArgumentException("사용중인 ID입니다.");
        });

        // Member 생성
        Member newMember = Member.builder()
                .memberId(memberDTO.getMemberId())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickName(memberDTO.getNickName())
                .name(memberDTO.getName())
                .gender(memberDTO.toGender())
                .build();

        if (memberDTO.getRoles() != null && !memberDTO.getRoles().isEmpty()) {
            Set<Roles> roles = memberDTO.getRoles().stream()
                    .map(roleName -> rolesRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new IllegalArgumentException("권한이 존재하지 않습니다: " + roleName)))
                    .collect(Collectors.toSet());

            // ROLE_ADMIN 권한이 있으면 ROLE_USER도 추가
            boolean isAdmin = roles.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getRoleName()));
            if (isAdmin) {
                Roles userRole = rolesRepository.findByRoleName("ROLE_USER")
                        .orElseThrow(() -> new IllegalArgumentException("권한이 존재하지 않습니다: ROLE_USER"));
                roles.add(userRole);
            }

            newMember.getRoles().addAll(roles);
        }
        

        memberRepository.save(newMember);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public void updateMember(Long memberNo, MemberDTO memberDTO) {
        // 기존 사용자 조회
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // ID 변경 처리
        if (!member.getMemberId().equals(memberDTO.getMemberId())) {
            memberRepository.findByMemberId(memberDTO.getMemberId()).ifPresent(existingMember -> {
                throw new IllegalArgumentException("사용중인 ID입니다.");
            });
            member.updateMemberId(memberDTO.getMemberId());
        }

        // 정보 업데이트
        member.updateInfo(memberDTO.getNickName(), memberDTO.getName(), memberDTO.toGender());

        // 비밀번호 변경
        if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
            member.updatePassword(passwordEncoder.encode(memberDTO.getPassword()));
        }

        // 권한 업데이트
        if (memberDTO.getRoles() != null && !memberDTO.getRoles().isEmpty()) {
            Set<Roles> roles = memberDTO.getRoles().stream()
                    .map(roleName -> rolesRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new IllegalArgumentException("권한이 존재하지 않습니다: " + roleName)))
                    .collect(Collectors.toSet());

            // ROLE_ADMIN 권한이 있으면 ROLE_USER도 추가
            boolean isAdmin = roles.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getRoleName()));
            if (isAdmin) {
                Roles userRole = rolesRepository.findByRoleName("ROLE_USER")
                        .orElseThrow(() -> new IllegalArgumentException("권한이 존재하지 않습니다: ROLE_USER"));
                roles.add(userRole);
            }

            member.getRoles().clear(); 		 // 권한 clear
            member.getRoles().addAll(roles); // 수정 된 권한 설정
        }
        
        
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    public void deleteMember(Long memberNo) {
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        memberRepository.delete(member);
    }
}