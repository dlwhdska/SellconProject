package com.sellcon.domain;

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
public class Cart {
	@Id
	@Column(name="cseq", length=5)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cseq;
	
	@Column(columnDefinition="char(1) default '1'", nullable = false)
	@Builder.Default
	private String result="1";
	
	// 회원 테이블과 연결 (member_id)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID", updatable = false)
	private Member member;
	
	// 판매 테이블과 연결(sseq)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sseq", referencedColumnName = "sseq", updatable=false)
	private Selling_Product selling_product;
	
	public Selling_Product getSellingProducts() {
		return this.selling_product;
	}

}
