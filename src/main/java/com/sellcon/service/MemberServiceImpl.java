package com.sellcon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Member;
import com.sellcon.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private Map<String, String> emailVerificationMap = new HashMap<>();

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EmailService emailService;
	
	@Override
	public Optional<Member> getMemberById(String id) {
		return memberRepository.findById(id);
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

			entity.setPwd(member.getPwd());
			entity.setName(member.getName());
			entity.setEmail(member.getEmail());
			entity.setPhone(member.getPhone());
			entity.setBank(member.getBank());
			entity.setAccount(member.getAccount());

			memberRepository.save(entity);
		}
	}
	
	@Override
	public void remove(Member member) {
		
		memberRepository.deleteById(member.getId());
	}

	@Override
	@Transactional
	public void resetPwdAndSendEmail(String email) {

		String temporaryPwd = generateTemporaryPwd();

		emailService.sendTemporaryPwd(email, temporaryPwd);

		Optional<Member> memberOptional = memberRepository.findByEmail(email);
		if (memberOptional.isPresent()) {
			Member member = memberOptional.get();
			member.setPwd(temporaryPwd);
			memberRepository.save(member);
		}
		;
	}

	private String generateTemporaryPwd() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}

	@Override
	public void requestVerificationCode(String name, String email) {
		Optional<Member> member = memberRepository.findByNameAndEmail(name, email);

		if (member.isPresent()) {
			String verificationCode = generateVerificationCode();
			emailService.sendVerificationCode(email, verificationCode);

			emailVerificationMap.put(email, verificationCode);
			System.out.println("service 1email=" + email);
			System.out.println("service 1verficationCode=" + verificationCode);
		} else {
			throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
		}
	}

	@Override
	public boolean verifyCodeAndRetrieveId(String email, String verificationCode) {
		System.out.println("service 2verificationCode: " + verificationCode);
		System.out.println("service 1 emailVerfiactionMap=" + emailVerificationMap);
		for (Map.Entry<String, String> entry : emailVerificationMap.entrySet()) {
			if (entry.getValue().equals(verificationCode)) {
				String storedEmail = entry.getKey();

				Optional<Member> member = memberRepository.findByEmail(storedEmail);
				return member.map(value -> {
					System.out.println("service 1stroedEmail=" + storedEmail);
					System.out.println("사용자의 아이디는: " + value.getId());
					System.out.println("serivce 3verficationCode=" + verificationCode);
					System.out.println("service 2storedEmail=" + storedEmail);
					return true;
				}).orElse(false);
			}
		}

		throw new RuntimeException("인증번호가 일치하지 않습니다.");
	}

	@Override
	public String generateVerificationCode() {

		Random random = new Random();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}

	@Override
	public String getIdByEmail(String email) {
		Optional<Member> member = memberRepository.findByEmail(email);
		System.out.println("service getIdByEmail member=" + member);
		System.out.println("serive getIdByEmail email=" + email);

		if (member.isPresent()) {
			Member foundMember = member.get();
			System.out.println("service Found member: " + foundMember);
			return foundMember.getId();
		} else {
			System.out.println("service No member found for email: " + email);
			return null;
		}
	}

	@Override
	public boolean checkNameEmailMatch(String name, String email) {
		Optional<Member> nameMatch = memberRepository.findByNameAndEmail(name, email);

		return nameMatch.isPresent();
	}

	@Override
	public boolean doesEmailExist(String email) {
		Optional<Member> member = memberRepository.findByEmail(email);
		return member.isPresent();
	}

	@Override
	public List<Member> getMemberList(Member member) {
		return (List<Member>) memberRepository.findAll();
	}

	@Override
	public void memberListModify(Member member) {
		Optional<Member> result = memberRepository.findById(member.getId());

		if (result.isPresent()) {
			Member entity = result.get();

			entity.setScamyn(member.getScamyn());

			memberRepository.save(entity);
		}
	}

	@Override
	public void memberListDelete(Member member) {
		
		memberRepository.deleteById(member.getId());
	}

}
