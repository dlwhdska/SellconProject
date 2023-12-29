package com.sellcon.service;

import java.util.List;
import java.util.Optional;

import com.sellcon.domain.Member;

public interface MemberService {

	Optional<Member> getMemberById(String id);
	
	Member joinMember(Member member);
	
	boolean idCheck(String id);
	
	boolean emailCheck(String email);
	
	void modify(Member member);
	
	void resetPwdAndSendEmail(String email);
	
	void requestVerificationCode(String name, String email);
	
	boolean verifyCodeAndRetrieveId(String email, String verificationCode);
	
	String generateVerificationCode();
	
	String getIdByEmail(String email);
	
	boolean checkNameEmailMatch(String name, String email);
	
	boolean doesEmailExist(String email);
	
	List<Member> getMemberList(Member member);
	
	void memberListModify(Member member);
	
	void memberListDelete(Member member);
	
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
