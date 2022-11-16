package com.spring.clinicMap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.clinicMap.entity.Member;
import com.spring.clinicMap.repository.MemberRepository;
import com.spring.clinicMap.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Member join(Member member) {
		//Member 유효성 검사
		if(member == null || member.getUsername() == null) {
			throw new RuntimeException("Invalid Argument");
		}
		
		//username 중복체크
		if(memberRepository.existsByUsername(member.getUsername())) {
			throw new RuntimeException("username already exists");
		}
		
		return memberRepository.save(member);
	}
	
	@Override
	public Member login(String username, String password) {
//		return memberRepository.findByUsernameAndPassword(username, password);
		
		Member loginMember = memberRepository.findByUsername(username);
		
		if(loginMember != null && passwordEncoder.matches(password, loginMember.getPassword())) {
			return loginMember;
		}
		
		return null;
}
}