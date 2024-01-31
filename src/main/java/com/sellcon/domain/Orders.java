package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "oseq", length=5)
    private Long oseq;
	
	@Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date regdate;
	
	// 회원 테이블과 연결 (member_id)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID", updatable = false)
	private Member member;

}
