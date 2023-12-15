package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Selling_Product {

	@Id
	@GeneratedValue
	@Column(name="sseq", columnDefinition = "NUMBER(5)")
	private Long sseq;
	
	@Column(columnDefinition = "number(7)", nullable=false)
	private int sellingprice;
	
	@Column(columnDefinition = "number(20)", nullable=false)
	private Long barcode;
	
	@Column(length = 1000)
	private String barcode_image;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date valid;

	@Column(insertable=false, updatable=false, columnDefinition="DATE default sysdate")
	private Date regdate;
	
	@ManyToOne
	@JoinColumn(name="Sell_ID", updatable=false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="pseq", updatable=false)
	private Product product;
	
}
