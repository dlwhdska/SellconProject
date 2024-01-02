package com.sellcon.domain;

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
public class Order_Detail {
	@Id
	@Column(name="odseq", columnDefinition = "NUMBER(5)")
	@GeneratedValue
	private Long odseq;
	
	// 주문 테이블 (oseq)
	@ManyToOne
	@JoinColumn(name="oseq", updatable=false)
	private Orders order;
	
	// 판매 테이블과 연결(sseq)
	@ManyToOne
	@JoinColumn(name="sseq", updatable=false)
	private Selling_Product selling_product;
	
}
