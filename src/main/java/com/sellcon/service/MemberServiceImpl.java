package com.sellcon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Member;
import com.sellcon.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public Member getMember(Member member) {

		Optional<Member> findMember = memberRepository.findById(member.getId());
		
		if (findMember.isPresent()) {
			return findMember.get();
		} else {
			return null;
		}
	}

	@Override
	public Member joinMember(Member member) {
		
		return memberRepository.save(member);
	}

	@Override
	public boolean idCheck(String id) {

		Optional<Member> idok = memberRepository.findById(id);
		
		return idok.isEmpty();
		
		
	}

	@Override
	public boolean emailCheck(String email) {
	
		Optional<Member> emailok = memberRepository.findByEmail(email);
		
		return emailok.isEmpty();
	}

	@Override
	public void modify(Member member) {

		Optional<Member> result = memberRepository.findById(member.getId());
		
		if (result.isPresent()) {
			Member entity = result.get();
			
			memberRepository.save(entity);
		}
	}
	
	
	

	
	
	
	
	
}
