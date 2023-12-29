package com.sellcon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellcon.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	Optional<Member> findById(String id);
	
	Optional<Member> findByEmail(String email);
	
	Optional<Member> findByNameAndEmail(String name, String email);
	
	Optional<Member> findByName(String name);
	
}
