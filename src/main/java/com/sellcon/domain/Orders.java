package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
	@Column(name="oseq", columnDefinition = "NUMBER(5)")
	@GeneratedValue
	private Long oseq;
	
	@Column(insertable=false, updatable=false, columnDefinition="date default sysdate")
	private Date regdate;
	
	// 회원 테이블과 연결 (member_id)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID", updatable = false)
	private Member member;

	//@OneToMany(mappedBy="orders")
	//private List<Order_Detail> orderdetails = new ArrayList<>();

}
