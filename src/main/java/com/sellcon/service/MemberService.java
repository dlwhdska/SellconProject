package com.sellcon.service;

import com.sellcon.domain.Member;

public interface MemberService {

	Member getMember(Member member);
	
	Member joinMember(Member member);
	
	boolean idCheck(String id);
	
	boolean emailCheck(String email);
	
	void modify(Member member);
	
	default Member dtoToEntity(Member member) {
		Member entity = member.builder()
				.id(member.getId())
				.pwd(member.getPwd())
				.name(member.getName())
				.email(member.getEmail())
				.phone(member.getPhone())
				.bank(member.getBank())
				.account(member.getAccount())
				.build();
		
		return entity;
	}
}
