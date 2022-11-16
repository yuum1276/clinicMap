package com.spring.clinicMap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.clinicMap.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	//username 중복 체크
	Boolean existsByUsername(String username);
			
	//로그인 처리
	//Member findByUsernameAndPassword(String username, String password);
			
	//비밀번호 일치 여부는 passwordEncoder로 진행
	Member findByUsername(String username);
}
